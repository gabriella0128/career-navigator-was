package com.gabi.career_navigator_was.domain.learning.service.da;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabi.career_navigator_was.domain.learning.dto.base.LearningGoalDto;
import com.gabi.career_navigator_was.domain.learning.entity.LearningGoal;
import com.gabi.career_navigator_was.domain.learning.mapper.LearningGoalMapper;
import com.gabi.career_navigator_was.domain.learning.repository.LearningGoalRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LearningGoalDataAccess {
	private final LearningGoalRepository repository;
	private final LearningGoalMapper mapper;

	@Transactional
	public LearningGoalDto save(LearningGoalDto learningGoalDto) {
		LearningGoal learningGoal = mapper.toEntity(learningGoalDto);
		LearningGoal saved = repository.save(learningGoal);
		return mapper.toDto(saved);
	}

	@Transactional(readOnly = true)
	public List<LearningGoalDto> findAllByPlanIdx(Long planIdx) {
		List<LearningGoal> learningGoals = repository.findAllByPlanIdx(planIdx);
		return mapper.getDtoList(learningGoals);
	}
}
