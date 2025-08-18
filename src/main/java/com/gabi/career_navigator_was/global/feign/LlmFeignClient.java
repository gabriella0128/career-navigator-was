package com.gabi.career_navigator_was.global.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.gabi.career_navigator_was.domain.interview.feign.dto.request.InterviewAnswerFeignReq;
import com.gabi.career_navigator_was.domain.interview.feign.dto.request.InterviewQuestionFeignReq;
import com.gabi.career_navigator_was.domain.interview.feign.dto.response.InterviewAnswerFeignRes;
import com.gabi.career_navigator_was.domain.interview.feign.dto.response.InterviewQuestionFeignRes;
import com.gabi.career_navigator_was.domain.learning.feign.dto.request.LearningFeignReq;
import com.gabi.career_navigator_was.domain.learning.feign.dto.response.LearningFeignRes;
import com.gabi.career_navigator_was.global.config.feign.LlmFeignConfig;
import com.gabi.career_navigator_was.global.dto.CommonResponse;


@FeignClient(name = "llmClient", url = "${feign.llm.url}", configuration = LlmFeignConfig.class)
public interface LlmFeignClient {

	@PostMapping(value="/generate-daily-questions",
		consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<CommonResponse<InterviewQuestionFeignRes>> generateDailyQuestions(
		@RequestBody InterviewQuestionFeignReq interviewFeignReq
	);

	@PostMapping(value="/generate-daily-question-answer",
		consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<CommonResponse<InterviewAnswerFeignRes>> generateDailyQuestionAnswer(
		@RequestBody InterviewAnswerFeignReq interviewAnswerFeignReq
	);

	@PostMapping("/generate-learning-plan")
	ResponseEntity<CommonResponse<LearningFeignRes>> generateLearningPlan(
		@RequestBody LearningFeignReq req
	);
}
