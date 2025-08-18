package com.gabi.career_navigator_was.domain.interview.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.ZoneId;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabi.career_navigator_was.domain.interview.dto.base.DailyAnswerDto;
import com.gabi.career_navigator_was.domain.interview.dto.base.DailyAnswerEvalDto;
import com.gabi.career_navigator_was.domain.interview.dto.base.DailyQuestionBatchDto;
import com.gabi.career_navigator_was.domain.interview.dto.base.DailyQuestionDto;
import com.gabi.career_navigator_was.domain.interview.dto.request.AnswerInsertReq;
import com.gabi.career_navigator_was.domain.interview.dto.request.QuestionDetailReq;
import com.gabi.career_navigator_was.domain.interview.dto.response.QuestionDetailRes;
import com.gabi.career_navigator_was.domain.interview.dto.response.inner.QuestionRes;
import com.gabi.career_navigator_was.domain.interview.feign.dto.request.InterviewAnswerFeignReq;
import com.gabi.career_navigator_was.domain.interview.feign.dto.request.InterviewQuestionFeignReq;
import com.gabi.career_navigator_was.domain.interview.feign.dto.response.InterviewAnswerFeignRes;
import com.gabi.career_navigator_was.domain.interview.feign.dto.response.InterviewQuestionFeignRes;
import com.gabi.career_navigator_was.domain.interview.feign.dto.response.inner.InterviewQuestionItemRes;
import com.gabi.career_navigator_was.domain.interview.service.InterviewService;
import com.gabi.career_navigator_was.domain.interview.service.da.DailyAnswerDataAccess;
import com.gabi.career_navigator_was.domain.interview.service.da.DailyAnswerEvalDataAccess;
import com.gabi.career_navigator_was.domain.interview.service.da.DailyQuestionBatchDataAccess;
import com.gabi.career_navigator_was.domain.interview.service.da.DailyQuestionDataAccess;
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
public class InterviewServiceImpl implements InterviewService {

	private final JwtTokenUtil jwtTokenUtil;
	private final UserInfoDataAccess userInfoDataAccess;
	private final ResumeDataAccess resumeDataAccess;

	private final DailyAnswerDataAccess dailyAnswerDataAccess;
	private final DailyAnswerEvalDataAccess dailyAnswerEvalDataAccess;

	private final DailyQuestionDataAccess dailyQuestionDataAccess;
	private final DailyQuestionBatchDataAccess dailyQuestionBatchDataAccess;

	private final ResumeService resumeService;
	private final LlmFeignClient llmFeignClient;
	private final ObjectMapper objectMapper;

	private static final ZoneId KST = ZoneId.of("Asia/Seoul");

	@Override
	public CommonResponse<Void> generateDailyQuestion() {
		Long userIdx = currentUserIdx();
		LocalDate todayKst = LocalDate.now(KST);

		assertNotExistsTodayBatch(userIdx, todayKst);

		ResumeDto representResume = findRepresentResume(userIdx)
			.orElseThrow(() -> new CarnavCustomException(CarnavCustomErrorCode.REPRESENT_RESUME_NOT_FOUND));

		DetailResumeRes resumeDetail = resumeService.buildDetailResume(representResume);
		String sessionUid = UUID.randomUUID().toString();

		List<String> recentQuestions = findRecentQuestions(userIdx);

		InterviewQuestionFeignReq feignReq = InterviewQuestionFeignReq.builder()
			.sessionUid(sessionUid)
			.resume(resumeDetail)
			.recentQuestions(recentQuestions)
			.build();

		InterviewQuestionFeignRes llmData = extractQuestionLlmData(
			llmFeignClient.generateDailyQuestions(feignReq)
		);

		String resumeSnapshot = toJson(resumeDetail);

		DailyQuestionBatchDto savedBatch = dailyQuestionBatchDataAccess.save(
			DailyQuestionBatchDto.builder()
				.userIdx(userIdx)
				.sessionUid(sessionUid)
				.practiceDate(todayKst)
				.resumeIdx(representResume.resumeIdx())
				.resumeSnapshot(resumeSnapshot)
				.seed(llmData.seed())
				.modelName(llmData.modelName())
				.promptVersion(llmData.promptVersion())
				.generatedAt(LocalDateTime.now(KST))
				.build()
		);

		List<DailyQuestionDto> questions = llmData.questions().stream()
			.map(q -> toDailyQuestionDto(q, savedBatch.batchIdx(), sessionUid))
			.toList();

		dailyQuestionDataAccess.saveAll(questions);

		return CommonResponse.success("오늘자 면접 질문 생성 완료", null);
	}

	private List<String> findRecentQuestions(Long userIdx) {
		LocalDate today = LocalDate.now(KST);
		LocalDate start = today.minusDays(14); // T-14
		LocalDate end   = today.minusDays(1);

		Set<Long> batchIdxes = dailyQuestionBatchDataAccess.findAllByUserIdxAndPracticeDateBetween(userIdx, start, end)
			.stream()
			.map(DailyQuestionBatchDto::batchIdx)
			.collect(Collectors.toSet());

		return dailyQuestionDataAccess.findByBatchIdxIn(new ArrayList<>(batchIdxes))
			.stream()
			.map(DailyQuestionDto::question)
			.toList();
	}

	@Override
	public CommonResponse<List<QuestionRes>> retrieveDailyQuestion() {
		Long userIdx = currentUserIdx();
		Optional<DailyQuestionBatchDto> dailyQuestionBatchDtoOpt =
			dailyQuestionBatchDataAccess.findByUserIdxAndPracticeDate(userIdx, LocalDate.now());

		if (dailyQuestionBatchDtoOpt.isEmpty()) {
			return CommonResponse.success("생성된 오늘자 면접 질문이 없습니다.", null);
		}

		DailyQuestionBatchDto dailyQuestionBatchDto = dailyQuestionBatchDtoOpt.get();
		List<DailyQuestionDto> dailyQuestionDtos = dailyQuestionDataAccess.findByBatchIdx(dailyQuestionBatchDto.batchIdx()).stream().sorted(
				Comparator.comparingInt(DailyQuestionDto::position))
			.toList();


		List<QuestionRes> returnData = dailyQuestionDtos.stream().map(dailyQuestionDto -> QuestionRes.builder()
			.questionIdx(dailyQuestionDto.questionIdx())
			.position(dailyQuestionDto.position())
			.question(dailyQuestionDto.question())
			.difficulty(dailyQuestionDto.difficulty())
			.answered(dailyAnswerDataAccess.existsByQuestionIdx(dailyQuestionDto.questionIdx()))
			.build()
		).toList();

		return CommonResponse.success("오늘자 면접 질문 조회 성공", returnData);
	}

	@Override
	public CommonResponse<QuestionDetailRes> retrieveDailyQuestionDetail(QuestionDetailReq body) {
		DailyQuestionDto dailyQuestionDto = dailyQuestionDataAccess.findByQuestionIdx(body.questionIdx())
			.orElseThrow(() -> new CarnavCustomException(CarnavCustomErrorCode.NOT_FOUND_RESOURCE));

		if (!dailyAnswerDataAccess.existsByQuestionIdx(body.questionIdx())) {
			QuestionDetailRes returnData = QuestionDetailRes.builder()
				.questionIdx(dailyQuestionDto.questionIdx())
				.question(dailyQuestionDto.question())
				.position(dailyQuestionDto.position())
				.build();

			return CommonResponse.success("면접 질문 상세 조회 성공", returnData);
		}

		DailyAnswerDto dailyAnswerDto = dailyAnswerDataAccess.findByQuestionIdx(dailyQuestionDto.questionIdx())
			.orElseThrow(() -> new CarnavCustomException(CarnavCustomErrorCode.NOT_FOUND_RESOURCE));

		DailyAnswerEvalDto dailyAnswerEvalDto = dailyAnswerEvalDataAccess.findByAnswerIdx(dailyAnswerDto.answerIdx())
			.orElseThrow(() -> new CarnavCustomException(CarnavCustomErrorCode.NOT_FOUND_RESOURCE));

		QuestionDetailRes returnData = QuestionDetailRes.builder()
			.questionIdx(dailyQuestionDto.questionIdx())
			.question(dailyQuestionDto.question())
			.position(dailyQuestionDto.position())
			.answer(dailyAnswerDto.answer())
			.scoreOverall(dailyAnswerEvalDto.scoreOverall())
			.scores(dailyAnswerEvalDto.scores())
			.feedback(dailyAnswerEvalDto.feedback())
			.strength(dailyAnswerEvalDto.strength())
			.improvements(dailyAnswerEvalDto.improvements())
			.build();

		return CommonResponse.success("면접 질문 상세 조회 성공", returnData);
	}

	@Override
	public CommonResponse<Void> generateDailyQuestionAnswer(AnswerInsertReq body) {
		Long userIdx = currentUserIdx();
		DailyQuestionDto dailyQuestionDto = dailyQuestionDataAccess.findByQuestionIdx(body.questionIdx())
			.orElseThrow(() -> new CarnavCustomException(CarnavCustomErrorCode.NOT_FOUND_RESOURCE));

		DailyAnswerDto savedDailyAnswer = dailyAnswerDataAccess.save(DailyAnswerDto.builder()
			.questionIdx(dailyQuestionDto.questionIdx())
			.userIdx(userIdx)
			.answer(body.answer())
			.submittedAt(LocalDateTime.now())
			.build());

		String sessionUid = UUID.randomUUID().toString();

		InterviewAnswerFeignReq interviewAnswerFeignReq = InterviewAnswerFeignReq.builder()
			.sessionUid(sessionUid)
			.question(dailyQuestionDto.question())
			.answer(body.answer())
			.build();

		ResponseEntity<CommonResponse<InterviewAnswerFeignRes>> resp = llmFeignClient.generateDailyQuestionAnswer(
			interviewAnswerFeignReq);

		InterviewAnswerFeignRes llmData = extractAnswerLlmData(resp);

		dailyAnswerEvalDataAccess.save(DailyAnswerEvalDto.builder()
			.answerIdx(savedDailyAnswer.answerIdx())
			.userIdx(userIdx)
			.scoreOverall(llmData.scoreOverall())
			.strength(llmData.scores())
			.feedback(llmData.feedback())
			.strength(llmData.strength())
			.improvements(llmData.improvements())
			.seed(llmData.seed())
			.modelName(llmData.modelName())
			.promptVersion(llmData.promptVersion())
			.evaluatedAt(llmData.evaluatedAt())
			.build());

		return CommonResponse.success("면접 질문 답변 생성 성공", null);
	}

	private Long currentUserIdx() {
		String userId = jwtTokenUtil.getUserIdFromToken();
		return userInfoDataAccess.findByUserId(userId)
			.orElseThrow(() -> new CarnavCustomException(CarnavCustomErrorCode.NOT_FOUND_USER))
			.userIdx();
	}

	private void assertNotExistsTodayBatch(Long userIdx, LocalDate practiceDate) {
		Optional<DailyQuestionBatchDto> opt =
			dailyQuestionBatchDataAccess.findByUserIdxAndPracticeDate(userIdx, practiceDate);
		if (opt.isPresent()) {
			throw new CarnavCustomException(CarnavCustomErrorCode.DAILY_QUESTION_ALREADY_EXIST);
		}
	}

	private Optional<ResumeDto> findRepresentResume(Long userIdx) {
		List<ResumeDto> all = resumeDataAccess.findAllByUserIdx(userIdx);
		return all.stream().filter(r -> YnType.Y.equals(r.representYn())).findFirst();
	}

	private InterviewQuestionFeignRes extractQuestionLlmData(ResponseEntity<CommonResponse<InterviewQuestionFeignRes>> resp) {
		return Optional.ofNullable(resp)
			.filter(r -> r.getStatusCode().is2xxSuccessful())
			.map(ResponseEntity::getBody)
			.filter(CommonResponse::isSuccess)
			.map(CommonResponse::getData)
			.orElseThrow(() -> new CarnavCustomException(CarnavCustomErrorCode.LLM_FEIGN_ERROR));
	}

	private InterviewAnswerFeignRes extractAnswerLlmData(ResponseEntity<CommonResponse<InterviewAnswerFeignRes>> resp) {
		return Optional.ofNullable(resp)
			.filter(r -> r.getStatusCode().is2xxSuccessful())
			.map(ResponseEntity::getBody)
			.filter(CommonResponse::isSuccess)
			.map(CommonResponse::getData)
			.orElseThrow(() -> new CarnavCustomException(CarnavCustomErrorCode.LLM_FEIGN_ERROR));
	}

	private String toJson(Object any) {
		try {
			return objectMapper.writeValueAsString(any);
		} catch (JsonProcessingException e) {
			throw new CarnavCustomException(CarnavCustomErrorCode.JSON_PARSING);
		}
	}

	private DailyQuestionDto toDailyQuestionDto(InterviewQuestionItemRes q, Long batchIdx, String sessionUid) {
		String expectedPointsJson = toJson(q.expectedPoints());
		String evidenceJson = toJson(q.evidence());

		return DailyQuestionDto.builder()
			.batchIdx(batchIdx)
			.sessionUid(sessionUid)
			.category(q.category())
			.difficulty(q.difficulty())
			.question(q.question())
			.expectedPoints(expectedPointsJson)
			.evidence(evidenceJson)
			.qHash(q.qHash())
			.position(q.position())
			.build();
	}
}
