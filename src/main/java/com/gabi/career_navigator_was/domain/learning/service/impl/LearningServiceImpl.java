package com.gabi.career_navigator_was.domain.learning.service.impl;

import static com.gabi.career_navigator_was.domain.interview.service.impl.InterviewServiceImpl.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabi.career_navigator_was.domain.interview.dto.base.DailyAnswerEvalDto;
import com.gabi.career_navigator_was.domain.interview.service.da.DailyAnswerEvalDataAccess;
import com.gabi.career_navigator_was.domain.learning.dto.base.LearningGoalDto;
import com.gabi.career_navigator_was.domain.learning.dto.base.LearningPlanDto;
import com.gabi.career_navigator_was.domain.learning.dto.base.LearningTaskDto;
import com.gabi.career_navigator_was.domain.learning.dto.response.LearningPlanRes;
import com.gabi.career_navigator_was.domain.learning.dto.response.inner.LearningGoalRes;
import com.gabi.career_navigator_was.domain.learning.dto.response.inner.LearningTaskRes;
import com.gabi.career_navigator_was.domain.learning.feign.dto.request.LearningFeignReq;
import com.gabi.career_navigator_was.domain.learning.feign.dto.response.LearningFeignRes;
import com.gabi.career_navigator_was.domain.learning.feign.dto.response.inner.LearningGoalItemRes;
import com.gabi.career_navigator_was.domain.learning.feign.dto.response.inner.LearningTaskItemRes;
import com.gabi.career_navigator_was.domain.learning.service.LearningService;
import com.gabi.career_navigator_was.domain.learning.service.da.LearningGoalDataAccess;
import com.gabi.career_navigator_was.domain.learning.service.da.LearningPlanDataAccess;
import com.gabi.career_navigator_was.domain.learning.service.da.LearningTaskDataAccess;
import com.gabi.career_navigator_was.domain.resume.dto.base.ResumeDto;
import com.gabi.career_navigator_was.domain.resume.dto.response.DetailResumeRes;
import com.gabi.career_navigator_was.domain.resume.service.ResumeService;
import com.gabi.career_navigator_was.domain.resume.service.da.ResumeDataAccess;
import com.gabi.career_navigator_was.domain.user.service.da.UserInfoDataAccess;
import com.gabi.career_navigator_was.global.code.CarnavCustomErrorCode;
import com.gabi.career_navigator_was.global.code.YnType;
import com.gabi.career_navigator_was.global.dto.CommonResponse;
import com.gabi.career_navigator_was.global.exception.CarnavCustomException;
import com.gabi.career_navigator_was.global.feign.LlmFeignClient;
import com.gabi.career_navigator_was.global.util.JwtTokenUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LearningServiceImpl implements LearningService {
	private final JwtTokenUtil jwtTokenUtil;
	private final LlmFeignClient llmFeignClient;
	private final ObjectMapper objectMapper;
	private final ResumeService resumeService;
	private final UserInfoDataAccess userInfoDataAccess;
	private final ResumeDataAccess resumeDataAccess;
	private final LearningPlanDataAccess learningPlanDataAccess;
	private final LearningGoalDataAccess learningGoalDataAccess;
	private final LearningTaskDataAccess learningTaskDataAccess;
	private final DailyAnswerEvalDataAccess dailyAnswerEvalDataAccess;

	private static ZoneId KST = ZoneId.of("Asia/Seoul");

	@Override
	public CommonResponse<LearningPlanRes> retrieveLearningPlan() {
		Long userIdx = currentUserIdx();

		LocalDate todayKst = LocalDate.now(KST);
		LocalDate periodStart = todayKst.withDayOfMonth(1);

		boolean planExists = learningPlanDataAccess.existsByUserIdxAndPeriodStart(userIdx, periodStart);

		if (!planExists) {
			return CommonResponse.success("생성된 이번 달 학습플랜이 없습니다.", null);
		}

		LearningPlanDto learningPlanDto = learningPlanDataAccess.findByUserIdxAndPeriodStart(userIdx, periodStart)
			.orElseThrow(() -> new CarnavCustomException(CarnavCustomErrorCode.NOT_FOUND_RESOURCE));

		Long planIdx = learningPlanDto.planIdx();
		List<LearningGoalDto> learningGoals = learningGoalDataAccess.findAllByPlanIdx(planIdx);
		List<LearningGoalRes> goalRes = learningGoals.stream().map(goal -> {
			List<LearningTaskRes> taskRes = learningTaskDataAccess.findAllByGoalIdx(goal.goalIdx())
				.stream()
				.map(taskDto -> LearningTaskRes.builder()
					.goalIdx(goal.goalIdx())
					.taskIdx(taskDto.taskIdx())
					.taskTitle(taskDto.taskTitle())
					.resourceUrl(taskDto.resourceUrl())
					.weekNo(taskDto.weekNo())
					.build())
				.toList();

			return LearningGoalRes.builder().
				goalIdx(goal.goalIdx())
				.title(goal.title())
				.metric(goal.metric())
				.targetValue(goal.targetValue())
				.priority(goal.priority())
				.learningTasks(taskRes).build();
		}).toList();

		LearningPlanRes returnData = LearningPlanRes.builder()
			.planIdx(planIdx)
			.planMonth(todayKst.getMonthValue())
			.learningGoals(goalRes)
			.build();

		return CommonResponse.success("학습 계획 조회 성공", returnData);

	}

	@Override
	public CommonResponse<Void> generateLearningPlan() {
		Long userIdx = currentUserIdx();

		LocalDate todayKst = LocalDate.now(KST);
		LocalDate periodStart = todayKst.withDayOfMonth(1);

		boolean planExists = learningPlanDataAccess.existsByUserIdxAndPeriodStart(userIdx, periodStart);

		if (planExists) {
			return CommonResponse.success("이미 플랜이 생성 되었습니다.", null);
		}

		ResumeDto represent = findRepresentResume(userIdx)
			.orElseThrow(() -> new CarnavCustomException(CarnavCustomErrorCode.REPRESENT_RESUME_NOT_FOUND));


		// 이력서 상세 스냅샷
		DetailResumeRes resumeSnapshotObj = resumeService.buildDetailResume(represent);
		String resumeSnapshotJson = toJson(resumeSnapshotObj);

		List<String> improvements = findRecentImprovements(userIdx);

		// 4) LLM 호출 준비 & 호출
		String sessionUid = UUID.randomUUID().toString();
		LearningFeignReq feignReq = LearningFeignReq.builder()
			.sessionUid(sessionUid)
			.resume(resumeSnapshotObj)
			.improvements(improvements)
			.build();

		ResponseEntity<CommonResponse<LearningFeignRes>> resp =
			llmFeignClient.generateLearningPlan(feignReq);

		LearningFeignRes data = extractData(resp);

		LearningPlanDto savedPlan = learningPlanDataAccess.save(
			LearningPlanDto.builder()
				.userIdx(userIdx)
				.planDate(todayKst)
				.periodStart(periodStart)
				.resumeIdx(represent.resumeIdx())
				.resumeSnapshot(resumeSnapshotJson)
				.seed(data.seed())
				.modelName(data.modelName())
				.promptVersion(data.promptVersion())
				.generatedAt(LocalDateTime.now(KST))
				.build()
		);

		int currentWeek = currentWeekOfMonth(todayKst); // 1..4(5주차도 4로 캡)

		for (LearningGoalItemRes g : Optional.ofNullable(data.goals()).orElseGet(List::of)) {
			// Goal 저장
			LearningGoalDto savedGoal = learningGoalDataAccess.save(
				LearningGoalDto.builder()
					.planIdx(savedPlan.planIdx())
					.title(nvl(g.title(), "학습 목표"))
					.metric(nvl(g.metric(), "정량 지표를 명시"))
					.targetValue(nvl(g.targetValue(), ""))
					.priority(nvlInt(g.priority(), 3))
					.category(nvl(g.category(), "process"))
					.build()
			);

			// Task 변환
			List<LearningTaskDto> mappedTasks = mapTaskItemsToDtos(
				Optional.ofNullable(g.tasks()).orElseGet(List::of),
				savedGoal.goalIdx()
			);

			List<LearningTaskDto> trimmed = trimAndAssignWeeks(mappedTasks, currentWeek);

			if (!trimmed.isEmpty()) {
				learningTaskDataAccess.saveAll(trimmed);
			}
		}

		return CommonResponse.success("이번 달 학습 플랜 생성 완료", null);
	}

	private String toJson(Object o) {
		try {
			return objectMapper.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			throw new CarnavCustomException(CarnavCustomErrorCode.JSON_PARSING);
		}
	}

	private LearningFeignRes extractData(ResponseEntity<CommonResponse<LearningFeignRes>> resp) {
		if (resp == null || !resp.getStatusCode().is2xxSuccessful() || resp.getBody() == null) {
			throw new CarnavCustomException(CarnavCustomErrorCode.LLM_FEIGN_ERROR);
		}
		CommonResponse<LearningFeignRes> body = resp.getBody();
		if (!body.isSuccess() || body.getData() == null) {
			throw new CarnavCustomException(CarnavCustomErrorCode.LLM_FEIGN_ERROR);
		}
		return body.getData();
	}

	/** 1:1~7, 2:8~14, 3:15~21, 4:22~28, 5:29~31 → 4로 캡 */
	private int currentWeekOfMonth(LocalDate d) {
		int w = ((d.getDayOfMonth() - 1) / 7) + 1;
		return Math.min(4, w);
	}

	private String nvl(String v, String def) {
		return (v == null || v.isBlank()) ? def : v;
	}

	private Integer nvlInt(Integer v, Integer def) {
		return (v == null) ? def : v;
	}

	/** LLM TaskItem → DTO 변환 (weekNo는 일단 그대로 두고 이후 TRIM에서 정리) */
	private List<LearningTaskDto> mapTaskItemsToDtos(List<LearningTaskItemRes> items, Long goalIdx) {
		return items.stream().map(t ->
			LearningTaskDto.builder()
				.taskIdx(null)
				.goalIdx(goalIdx)
				.taskTitle(nvl(t.taskTitle(), "학습 과제"))
				.resourceUrl(t.resourceUrl())
				.weekNo(t.weekNo()) // null일 수 있음 → TRIM에서 보정
				.build()
		).collect(Collectors.toList());
	}

	private List<LearningTaskDto> trimAndAssignWeeks(List<LearningTaskDto> tasks, int currentWeek) {
		List<LearningTaskDto> out = new ArrayList<>();
		int w = currentWeek;

		for (LearningTaskDto t : tasks) {
			Integer orig = t.weekNo();
			if (orig != null && orig < currentWeek) continue;

			int assign = (orig == null || orig < currentWeek) ? w : orig;
			out.add(t.toBuilder().weekNo(assign).build());

			if (orig == null) {
				w = Math.min(4, w + 1);
			}
		}
		return out;
	}

	private Optional<ResumeDto> findRepresentResume(Long userIdx) {
		List<ResumeDto> all = resumeDataAccess.findAllByUserIdx(userIdx);
		return all.stream().filter(r -> YnType.Y.equals(r.representYn())).findFirst();
	}

	private Long currentUserIdx() {
		String userId = jwtTokenUtil.getUserIdFromToken();
		return userInfoDataAccess.findByUserId(userId)
			.orElseThrow(() -> new CarnavCustomException(CarnavCustomErrorCode.NOT_FOUND_USER))
			.userIdx();
	}

	private List<String> findRecentImprovements(Long userIdx) {
		ZoneId zone = ZoneId.of("Asia/Seoul");
		LocalDate today = LocalDate.now(zone);

		LocalDate startDate = today.minusDays(13);
		LocalDate endDateExclusive = today.plusDays(1);

		LocalDateTime startDt = startDate.atStartOfDay();
		LocalDateTime endDtExclusive = endDateExclusive.atStartOfDay();

		return dailyAnswerEvalDataAccess
			.findAllByUserIdxAndEvaluatedAtBetween(
				userIdx, startDt, endDtExclusive
			)
			.stream()
			.map(DailyAnswerEvalDto::improvements)
			.filter(Objects::nonNull)
			.map(String::trim)
			.filter(s -> !s.isEmpty())
			.toList();
	}
}
