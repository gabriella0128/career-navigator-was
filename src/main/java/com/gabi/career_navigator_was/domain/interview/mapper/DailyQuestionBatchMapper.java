package com.gabi.career_navigator_was.domain.interview.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.gabi.career_navigator_was.domain.interview.dto.base.DailyQuestionBatchDto;
import com.gabi.career_navigator_was.domain.interview.entity.DailyQuestionBatch;
import com.gabi.career_navigator_was.global.mapper.GenericMapper;

@Mapper(
	componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface DailyQuestionBatchMapper extends GenericMapper<DailyQuestionBatchDto, DailyQuestionBatch> {
	@Override
	DailyQuestionBatchDto toDto(DailyQuestionBatch entity);

	@Override
	DailyQuestionBatch toEntity(DailyQuestionBatchDto dto);

	@Override
	List<DailyQuestionBatchDto> getDtoList(List<DailyQuestionBatch> entityList);

	@Override
	List<DailyQuestionBatch> getEntityList(List<DailyQuestionBatchDto> dtoList);
}
