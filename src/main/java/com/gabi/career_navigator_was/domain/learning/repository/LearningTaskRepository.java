package com.gabi.career_navigator_was.domain.learning.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabi.career_navigator_was.domain.learning.entity.LearningTask;

public interface LearningTaskRepository extends JpaRepository<LearningTask, Long> {
	List<LearningTask> findAllByGoalIdx(Long goalIdx);

	List<LearningTask> findAllByGoalIdxIn(List<Long> goalIdx);
}
