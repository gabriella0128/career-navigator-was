package com.gabi.career_navigator_was.domain.learning.service.da;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabi.career_navigator_was.domain.learning.dto.base.LearningTaskDto;
import com.gabi.career_navigator_was.domain.learning.entity.LearningTask;
import com.gabi.career_navigator_was.domain.learning.mapper.LearningTaskMapper;
import com.gabi.career_navigator_was.domain.learning.repository.LearningTaskRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LearningTaskDataAccess {
	private final LearningTaskRepository repository;
	private final LearningTaskMapper mapper;

	@Transactional
	public LearningTaskDto save(LearningTaskDto learningTaskDto) {
		LearningTask learningTask = mapper.toEntity(learningTaskDto);
		LearningTask saved = repository.save(learningTask);
		return mapper.toDto(saved);
	}

	@Transactional
	public List<LearningTaskDto> saveAll(List<LearningTaskDto> learningTaskDtos) {
		List<LearningTask> learningTasks = mapper.getEntityList(learningTaskDtos);
		List<LearningTask> saved = repository.saveAll(learningTasks);
		return mapper.getDtoList(saved);
	}

	@Transactional(readOnly = true)
	public List<LearningTaskDto> findAllByGoalIdx(Long goalIdx) {
		List<LearningTask> learningTasks = repository.findAllByGoalIdx(goalIdx);
		return mapper.getDtoList(learningTasks);
	}

	@Transactional(readOnly = true)
	public List<LearningTaskDto> findAllByGoalIdxIn(List<Long> goalIdx) {
		List<LearningTask> learningTasks = repository.findAllByGoalIdxIn(goalIdx);
		return mapper.getDtoList(learningTasks);
	}
}
