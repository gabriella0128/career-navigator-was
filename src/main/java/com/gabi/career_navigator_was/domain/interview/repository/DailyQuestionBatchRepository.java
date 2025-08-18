package com.gabi.career_navigator_was.domain.interview.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabi.career_navigator_was.domain.interview.entity.DailyQuestionBatch;

public interface DailyQuestionBatchRepository extends JpaRepository<DailyQuestionBatch, Long> {
	Optional<DailyQuestionBatch> findByUserIdxAndPracticeDate(Long userIdx, LocalDate practiceDate);

	List<DailyQuestionBatch> findAllByUserIdxAndPracticeDateBetween(Long userIdx, LocalDate start, LocalDate end);
}
