package com.gabi.career_navigator_was.domain.interview.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.gabi.career_navigator_was.domain.interview.dto.base.DailyAnswerEvalDto;
import com.gabi.career_navigator_was.domain.interview.entity.DailyAnswerEval;
import com.gabi.career_navigator_was.global.mapper.GenericMapper;
@Mapper(
	componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface DailyAnswerEvalMapper extends GenericMapper<DailyAnswerEvalDto, DailyAnswerEval> {
}
