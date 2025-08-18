package com.gabi.career_navigator_was.domain.interview.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabi.career_navigator_was.domain.interview.entity.DailyAnswerEval;

public interface DailyAnswerEvalRepository extends JpaRepository<DailyAnswerEval, Long> {

	Optional<DailyAnswerEval> findByAnswerIdx(Long answerIdx);

	List<DailyAnswerEval> findAllByUserIdxAndEvaluatedAtBetween(Long userIdx, LocalDateTime start, LocalDateTime end);

	List<DailyAnswerEval> findAllByUserIdx(Long userIdx);

	List<DailyAnswerEval> findAllByAnswerIdxIn(List<Long> answerIdx);
}
