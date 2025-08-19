package com.gabi.career_navigator_was.domain.dashboard.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabi.career_navigator_was.domain.dashboard.dto.response.DashboardRes;
import com.gabi.career_navigator_was.domain.dashboard.dto.response.inner.CalendarBlock;
import com.gabi.career_navigator_was.domain.dashboard.dto.response.inner.CalendarDay;
import com.gabi.career_navigator_was.domain.dashboard.dto.response.inner.Highlights;
import com.gabi.career_navigator_was.domain.dashboard.dto.response.inner.KpiItem;
import com.gabi.career_navigator_was.domain.dashboard.repository.querydsl.DashboardRepositoryCustom;
import com.gabi.career_navigator_was.domain.dashboard.service.DashboardService;
import com.gabi.career_navigator_was.domain.interview.dto.base.DailyAnswerEvalDto;
import com.gabi.career_navigator_was.domain.user.service.da.UserInfoDataAccess;
import com.gabi.career_navigator_was.global.code.CarnavCustomErrorCode;
import com.gabi.career_navigator_was.global.dto.CommonResponse;
import com.gabi.career_navigator_was.global.exception.CarnavCustomException;
import com.gabi.career_navigator_was.global.util.JwtTokenUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

	private static final ZoneId KST = ZoneId.of("Asia/Seoul");

	private final JwtTokenUtil jwtTokenUtil;
	private final UserInfoDataAccess userInfoDataAccess;

	private final DashboardRepositoryCustom dashboardRepositoryCustom;


	private final ObjectMapper objectMapper;

	@Override
	public CommonResponse<DashboardRes> retrieveDashboard() {
		Long userIdx = currentUserIdx();

		LocalDate today = LocalDate.now(KST);

		LocalDateTime dayStart = today.atStartOfDay();
		LocalDateTime dayEnd = dayStart.plusDays(1);

		LocalDate monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

		LocalDateTime weekStart = monday.atStartOfDay();
		LocalDateTime weekEnd = weekStart.plusDays(7);

		LocalDate monthFirst = today.withDayOfMonth(1);
		LocalDateTime monthStart = monthFirst.atStartOfDay();
		LocalDateTime monthEnd = monthStart.plusMonths(1);

		LocalDateTime sixMonthsStart = today.minusMonths(6).atStartOfDay();
		LocalDateTime streakStart = today.minusDays(59).atStartOfDay();

		// KPI
		Map<String, Integer> kpiMap = dashboardRepositoryCustom.findKpis(userIdx, dayStart, dayEnd, weekStart, weekEnd, monthStart, monthEnd);

		// Streak
		int streak = calcCurrentStreak(dashboardRepositoryCustom.distinctSubmitDates(userIdx, streakStart, dayEnd), today);

		// Histogram / Category
		var histogram = dashboardRepositoryCustom.findScoreHistogram(userIdx, sixMonthsStart, dayEnd);
		var catStats  = dashboardRepositoryCustom.findCategoryStats(userIdx, sixMonthsStart, dayEnd);

		// Calendar (당월)
		YearMonth ym = YearMonth.of(today.getYear(), today.getMonth());
		Map<LocalDate, Integer> byDay = dashboardRepositoryCustom.countPerDay(userIdx, monthStart, monthEnd);
		List<CalendarDay> days = new ArrayList<>();
		for (int d=1; d<=ym.lengthOfMonth(); d++) {
			LocalDate date = ym.atDay(d);
			days.add(new CalendarDay(date.toString(), byDay.getOrDefault(date, 0)));
		}
		CalendarBlock cal = new CalendarBlock(ym.toString(), days);

		// Highlights
		LocalDateTime hiStart = today.minusDays(13).atStartOfDay();
		List<DailyAnswerEvalDto> evals = dashboardRepositoryCustom.findEvalsForHighlights(userIdx, hiStart, dayEnd);
		Highlights hi = buildHighlightsFromEvals(evals);

		DashboardRes returnData = DashboardRes.builder()
			.kpis(List.of(
				new KpiItem("오늘",      kpiMap.getOrDefault("today_cnt",0)),
				new KpiItem("이번 주",   kpiMap.getOrDefault("week_cnt",0)),
				new KpiItem("이번 달",   kpiMap.getOrDefault("month_cnt",0)),
				new KpiItem("현재 연속",  streak)
			))
			.scoreHistogram(histogram)
			.categoryStats(catStats)
			.calendar(cal)
			.highlights(hi)
			.build();

		return CommonResponse.success("대시 보드 조회 성공", returnData);
	}

	private Highlights buildHighlightsFromEvals(List<DailyAnswerEvalDto> evals) {
		String recent = evals.stream()
			.map(DailyAnswerEvalDto::feedback)
			.filter(s -> s!=null && !s.isBlank())
			.findFirst().orElse("");

		List<String> strengths = new ArrayList<>();
		List<String> improvements = new ArrayList<>();
		for (var e : evals) {
			strengths.addAll(parseList(e.strength()));
			improvements.addAll(parseList(e.improvements()));
		}
		return new Highlights(recent, topK(strengths,3), topK(improvements,3));
	}

	private List<String> parseList(String raw) {
		if (!notBlank(raw)) return List.of();

		// 1) JSON 배열 시도
		try {
			List<String> arr = objectMapper.readValue(raw, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {});
			return arr.stream().map(this::normalizeTag).filter(this::notBlank).toList();
		} catch (Exception ignore) {}

		// 2) JSON 객체 내부 배열 시도
		try {
			Map<String, Object> m = objectMapper.readValue(raw, new com.fasterxml.jackson.core.type.TypeReference<Map<String, Object>>() {});
			// 흔히 쓸 법한 키 후보
			for (String key : List.of("items", "values", "list", "data")) {
				Object v = m.get(key);
				if (v instanceof List<?> list) {
					return list.stream().map(String::valueOf).map(this::normalizeTag).filter(this::notBlank).toList();
				}
			}
		} catch (Exception ignore) {}

		// 3) 구분자 기반 폴백 (콤마/세미콜론/개행/탭)
		return Arrays.stream(raw.split("[,;\\n\\t]+"))
			.map(this::normalizeTag)
			.filter(this::notBlank)
			.toList();
	}

	private List<String> topK(List<String> items, int topK) {
		if (items == null || items.isEmpty() || topK <= 0) return List.of();

		Map<String, Long> freq = items.stream()
			.map(this::normalizeTag)
			.filter(this::notBlank)
			.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

		return freq.entrySet().stream()
			.sorted(
				Comparator.<Map.Entry<String, Long>>comparingLong(e -> -e.getValue())
					.thenComparing(Map.Entry::getKey)
			)
			.limit(topK)
			.map(Map.Entry::getKey)
			.toList();
	}

	private Long currentUserIdx() {
		String userId = jwtTokenUtil.getUserIdFromToken();
		return userInfoDataAccess.findByUserId(userId)
			.orElseThrow(() -> new CarnavCustomException(CarnavCustomErrorCode.NOT_FOUND_USER))
			.userIdx();
	}


	/** 문자열이 비어있지 않은지 */
	private boolean notBlank(String s) {
		return s != null && !s.trim().isEmpty();
	}


	/** 한국어 라벨 정규화: 앞뒤 공백 제거, 연속 공백 축약 */
	private String normalizeTag(String s) {
		String t = s == null ? "" : s.trim();
		// 연속 공백 하나로
		t = t.replaceAll("\\s{2,}", " ");
		return t;
	}

	private int calcCurrentStreak(Set<LocalDate> daysKst, LocalDate todayKst) {
		int cur = 0;
		LocalDate d = todayKst;
		while (daysKst.contains(d)) {
			cur++;
			d = d.minusDays(1);
		}
		return cur;
	}

}