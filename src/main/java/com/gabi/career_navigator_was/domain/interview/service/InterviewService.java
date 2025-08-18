package com.gabi.career_navigator_was.domain.interview.service;

import java.util.List;

import com.gabi.career_navigator_was.domain.interview.dto.request.AnswerInsertReq;
import com.gabi.career_navigator_was.domain.interview.dto.request.QuestionDetailReq;
import com.gabi.career_navigator_was.domain.interview.dto.response.QuestionDetailRes;
import com.gabi.career_navigator_was.domain.interview.dto.response.inner.QuestionRes;
import com.gabi.career_navigator_was.global.dto.CommonResponse;

public interface InterviewService {

	CommonResponse<Void> generateDailyQuestion();

	CommonResponse<List<QuestionRes>> retrieveDailyQuestion();

	CommonResponse<QuestionDetailRes> retrieveDailyQuestionDetail(QuestionDetailReq body);

	CommonResponse<Void> generateDailyQuestionAnswer(AnswerInsertReq body);
}
