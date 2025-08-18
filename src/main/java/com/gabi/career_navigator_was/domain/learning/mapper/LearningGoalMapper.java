package com.gabi.career_navigator_was.domain.learning.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.gabi.career_navigator_was.domain.learning.dto.base.LearningGoalDto;
import com.gabi.career_navigator_was.domain.learning.entity.LearningGoal;
import com.gabi.career_navigator_was.global.mapper.GenericMapper;

@Mapper(
	componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface LearningGoalMapper extends GenericMapper<LearningGoalDto, LearningGoal> {
	@Override
	LearningGoalDto toDto(LearningGoal entity);

	@Override
	LearningGoal toEntity(LearningGoalDto dto);

	@Override
	List<LearningGoalDto> getDtoList(List<LearningGoal> entityList);

	@Override
	List<LearningGoal> getEntityList(List<LearningGoalDto> dtoList);
}
