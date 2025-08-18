package com.gabi.career_navigator_was.domain.interview.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabi.career_navigator_was.domain.interview.entity.DailyQuestion;

public interface DailyQuestionRepository extends JpaRepository<DailyQuestion, Long> {

	List<DailyQuestion> findByBatchIdx(Long batchIdx);

	List<DailyQuestion> findByBatchIdxIn(List<Long> batchIdx);

	Optional<DailyQuestion> findByQuestionIdx(Long questionIdx);

	List<DailyQuestion> findAllByQuestionIdxIn(List<Long> questionIdx);
}
