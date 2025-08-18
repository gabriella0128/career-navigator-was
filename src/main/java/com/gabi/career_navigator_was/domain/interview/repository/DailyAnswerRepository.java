package com.gabi.career_navigator_was.domain.interview.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gabi.career_navigator_was.domain.interview.dto.base.DailyAnswerDto;
import com.gabi.career_navigator_was.domain.interview.entity.DailyAnswer;

public interface DailyAnswerRepository extends JpaRepository<DailyAnswer, Long> {
	boolean existsByQuestionIdx(Long questionIdx);

	Optional<DailyAnswer> findByQuestionIdx(Long questionIdx);

	int countByUserIdxAndSubmittedAtBetween(Long userIdx, LocalDateTime start, LocalDateTime end);

	@Query("select a.submittedAt from DailyAnswer a where a.userIdx = :userIdx and a.submittedAt >= :start and a.submittedAt <  :end order by a.submittedAt desc")
	List<LocalDateTime> findSubmitTimes(@Param("userIdx") Long userIdx, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

	List<DailyAnswer> findAllByUserIdxAndSubmittedAtBetween(Long userIdx, LocalDateTime start, LocalDateTime end);

}
