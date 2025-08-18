package com.gabi.career_navigator_was.domain.dashboard.repository.querydsl.impl;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.gabi.career_navigator_was.domain.dashboard.dto.response.inner.CategoryStat;
import com.gabi.career_navigator_was.domain.dashboard.dto.response.inner.ScoreBin;
import com.gabi.career_navigator_was.domain.dashboard.repository.querydsl.DashboardRepositoryCustom;
import com.gabi.career_navigator_was.domain.interview.dto.base.DailyAnswerEvalDto;
import com.gabi.career_navigator_was.domain.interview.entity.QDailyAnswer;
import com.gabi.career_navigator_was.domain.interview.entity.QDailyAnswerEval;
import com.gabi.career_navigator_was.domain.interview.entity.QDailyQuestion;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.DateTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.core.types.Projections;

import lombok.RequiredArgsConstructor;
@Repository
@RequiredArgsConstructor
public class DashboardRepositoryCustomImpl implements DashboardRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	QDailyAnswer a = QDailyAnswer.dailyAnswer;
	QDailyAnswerEval e = QDailyAnswerEval.dailyAnswerEval;
	QDailyQuestion q = QDailyQuestion.dailyQuestion;

	Expression<java.sql.Date> dayExpr =
		Expressions.dateTemplate(java.sql.Date.class, "DATE({0})", a.submittedAt);

	@Override
	public Map<String, Integer> findKpis(Long userIdx, LocalDateTime dayStart, LocalDateTime dayEnd, LocalDateTime weekStart, LocalDateTime weekEnd, LocalDateTime monthStart, LocalDateTime monthEnd) {

		NumberExpression<Integer> todayCnt = Expressions.cases()
			.when(a.submittedAt.goe(dayStart).and(a.submittedAt.lt(dayEnd))).then(1).otherwise(0);
		NumberExpression<Integer> weekCnt  = Expressions.cases()
			.when(a.submittedAt.goe(weekStart).and(a.submittedAt.lt(weekEnd))).then(1).otherwise(0);
		NumberExpression<Integer> monthCnt = Expressions.cases()
			.when(a.submittedAt.goe(monthStart).and(a.submittedAt.lt(monthEnd))).then(1).otherwise(0);

		Tuple t = queryFactory.select(todayCnt.sum(), weekCnt.sum(), monthCnt.sum())
			.from(a)
			.where(a.userIdx.eq(userIdx)
				.and(a.submittedAt.goe(monthStart))   // 범위를 타이트하게
				.and(a.submittedAt.lt(dayEnd)))
			.fetchOne();

		Map<String, Integer> out = new HashMap<>();
		out.put("today_cnt",  Optional.ofNullable(t.get(0, Integer.class)).orElse(0));
		out.put("week_cnt",   Optional.ofNullable(t.get(1, Integer.class)).orElse(0));
		out.put("month_cnt",  Optional.ofNullable(t.get(2, Integer.class)).orElse(0));
		return out;
	}
	@Override/* ---------------- Score Histogram (1~5) ---------------- */
	public List<ScoreBin> findScoreHistogram(Long userIdx,
		LocalDateTime start, LocalDateTime end) {
		List<Tuple> rows = queryFactory.select(e.scoreOverall, e.count())
			.from(e)
			.join(a).on(a.answerIdx.eq(e.answerIdx))
			.where(a.userIdx.eq(userIdx)
				.and(a.submittedAt.goe(start))
				.and(a.submittedAt.lt(end)))
			.groupBy(e.scoreOverall)
			.fetch();

		Map<Integer, Integer> cnt = new HashMap<>();
		for (Tuple r : rows) {
			Integer score = r.get(e.scoreOverall);
			Integer c     = Objects.requireNonNull(r.get(1, Long.class)).intValue();
			cnt.put(score, c);
		}
		// 1~5 빠짐없이 채우기
		List<ScoreBin> out = new ArrayList<>(5);
		for (int s = 1; s <= 5; s++) out.add(new ScoreBin(s, cnt.getOrDefault(s, 0)));
		return out;
	}

	@Override/* ---------------- Category Stats (avg, count) ---------------- */
	public List<CategoryStat> findCategoryStats(Long userIdx,
		LocalDateTime start, LocalDateTime end) {
		List<Tuple> rows = queryFactory.select(q.category, e.scoreOverall.avg(), e.count())
			.from(e)
			.join(a).on(a.answerIdx.eq(e.answerIdx))
			.join(q).on(q.questionIdx.eq(a.questionIdx))
			.where(a.userIdx.eq(userIdx)
				.and(a.submittedAt.goe(start))
				.and(a.submittedAt.lt(end)))
			.groupBy(q.category)
			.fetch();

		return rows.stream()
			.map(t -> {
				String cat = t.get(q.category);
				double avg = Optional.ofNullable(t.get(e.scoreOverall.avg())).orElse(0.0);
				int cnt    = Optional.ofNullable(t.get(2, Long.class)).orElse(0L).intValue();
				// 1~5 클램프(필요 시)
				if (cnt > 0) avg = Math.max(1.0, Math.min(5.0, avg));
				return new CategoryStat(cat, round2(avg), cnt);
			})
			.sorted(Comparator.<CategoryStat>comparingInt(c -> -c.answers())
				.thenComparing(CategoryStat::category))
			.toList();
	}

	private double round2(double v) {
		return BigDecimal.valueOf(v).setScale(2, java.math.RoundingMode.HALF_UP).doubleValue();
	}

	@Override/* ---------------- Calendar: 해당 월 일자별 COUNT ---------------- */
	public Map<LocalDate, Integer> countPerDay(Long userIdx,
		LocalDateTime start, LocalDateTime end) {

		List<Tuple> rows = queryFactory.select(dayExpr, a.count())
			.from(a)
			.where(a.userIdx.eq(userIdx)
				.and(a.submittedAt.goe(start))
				.and(a.submittedAt.lt(end)))
			.groupBy(dayExpr)
			.fetch();

		Map<LocalDate, Integer> map = new HashMap<>();
		for (Tuple r : rows) {
			LocalDate d = Objects.requireNonNull(r.get(dayExpr)).toLocalDate();
			int c = Optional.ofNullable(r.get(1, Long.class)).orElse(0L).intValue();
			map.put(d, c);
		}
		return map;
	}

	@Override/* ---------------- Streak: 최근 60일 DISTINCT DATE ---------------- */
	public Set<LocalDate> distinctSubmitDates(Long userIdx,
		LocalDateTime start, LocalDateTime end) {
		return new HashSet<>(
			queryFactory.select(dayExpr)
				.distinct()
				.from(a)
				.where(a.userIdx.eq(userIdx)
					.and(a.submittedAt.goe(start))
					.and(a.submittedAt.lt(end)))
				.fetch()
		).stream().map(Date::toLocalDate).collect(Collectors.toSet());
	}

	@Override/* ---------------- Highlights: 최근 N일 평가 행 (필요 필드만) ---------------- */
	public List<DailyAnswerEvalDto> findEvalsForHighlights(Long userIdx,
		LocalDateTime start, LocalDateTime end) {
		return queryFactory.select(Projections.constructor(
				DailyAnswerEvalDto.class,
				e.evalIdx, e.answerIdx, e.userIdx, e.scoreOverall,
				e.scores, e.feedback, e.strength, e.improvements,
				e.seed, e.modelName, e.promptVersion, e.evaluatedAt
			))
			.from(e)
			.join(a).on(a.answerIdx.eq(e.answerIdx))
			.where(a.userIdx.eq(userIdx)
				.and(e.evaluatedAt.goe(start))
				.and(e.evaluatedAt.lt(end)))
			.orderBy(e.evaluatedAt.desc())
			.fetch();
	}
}



