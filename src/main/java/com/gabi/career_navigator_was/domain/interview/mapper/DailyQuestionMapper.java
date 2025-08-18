package com.gabi.career_navigator_was.domain.interview.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.gabi.career_navigator_was.domain.interview.dto.base.DailyQuestionDto;
import com.gabi.career_navigator_was.domain.interview.entity.DailyQuestion;
import com.gabi.career_navigator_was.global.mapper.GenericMapper;

@Mapper(
	componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface DailyQuestionMapper extends GenericMapper<DailyQuestionDto, DailyQuestion> {
	@Override
	DailyQuestionDto toDto(DailyQuestion entity);

	@Override
	DailyQuestion toEntity(DailyQuestionDto dto);

	@Override
	List<DailyQuestionDto> getDtoList(List<DailyQuestion> entityList);

	@Override
	List<DailyQuestion> getEntityList(List<DailyQuestionDto> dtoList);
}
