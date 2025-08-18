package com.gabi.career_navigator_was.domain.learning.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.gabi.career_navigator_was.domain.learning.dto.base.LearningTaskDto;
import com.gabi.career_navigator_was.domain.learning.entity.LearningTask;
import com.gabi.career_navigator_was.global.mapper.GenericMapper;

@Mapper(
	componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface LearningTaskMapper extends GenericMapper<LearningTaskDto, LearningTask> {
	@Override
	LearningTaskDto toDto(LearningTask entity);

	@Override
	LearningTask toEntity(LearningTaskDto dto);

	@Override
	List<LearningTaskDto> getDtoList(List<LearningTask> entityList);

	@Override
	List<LearningTask> getEntityList(List<LearningTaskDto> dtoList);
}
