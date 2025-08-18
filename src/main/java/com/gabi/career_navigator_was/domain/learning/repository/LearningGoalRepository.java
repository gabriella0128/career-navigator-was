package com.gabi.career_navigator_was.domain.learning.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabi.career_navigator_was.domain.learning.entity.LearningGoal;

public interface LearningGoalRepository extends JpaRepository<LearningGoal, Long> {

	List<LearningGoal> findAllByPlanIdx(Long planIdx);
}
