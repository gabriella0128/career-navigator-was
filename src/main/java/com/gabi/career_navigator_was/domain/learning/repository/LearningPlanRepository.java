package com.gabi.career_navigator_was.domain.learning.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabi.career_navigator_was.domain.learning.entity.LearningPlan;

public interface LearningPlanRepository extends JpaRepository<LearningPlan,Long> {

	boolean existsByUserIdxAndPeriodStart(Long userIdx, LocalDate periodStart);

	Optional<LearningPlan> findByUserIdxAndPeriodStart(Long userIdx, LocalDate periodStart);
}
