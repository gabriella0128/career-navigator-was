package com.gabi.career_navigator_was.domain.learning.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.gabi.career_navigator_was.domain.learning.dto.base.LearningPlanDto;
import com.gabi.career_navigator_was.domain.learning.entity.LearningPlan;
import com.gabi.career_navigator_was.global.mapper.GenericMapper;

@Mapper(
	componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface LearningPlanMapper extends GenericMapper<LearningPlanDto, LearningPlan> {
	@Override
	LearningPlanDto toDto(LearningPlan entity);

	@Override
	LearningPlan toEntity(LearningPlanDto dto);

	@Override
	List<LearningPlanDto> getDtoList(List<LearningPlan> entityList);

	@Override
	List<LearningPlan> getEntityList(List<LearningPlanDto> dtoList);
}
