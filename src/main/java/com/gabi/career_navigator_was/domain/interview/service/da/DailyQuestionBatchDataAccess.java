package com.gabi.career_navigator_was.domain.interview.service.da;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabi.career_navigator_was.domain.interview.dto.base.DailyQuestionBatchDto;
import com.gabi.career_navigator_was.domain.interview.entity.DailyQuestionBatch;
import com.gabi.career_navigator_was.domain.interview.mapper.DailyQuestionBatchMapper;
import com.gabi.career_navigator_was.domain.interview.repository.DailyQuestionBatchRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DailyQuestionBatchDataAccess {
	private final DailyQuestionBatchRepository repository;
	private final DailyQuestionBatchMapper mapper;

	@Transactional
	public DailyQuestionBatchDto save(DailyQuestionBatchDto dailyQuestionBatchDto) {
		DailyQuestionBatch dailyQuestionBatch = mapper.toEntity(dailyQuestionBatchDto);
		DailyQuestionBatch saved = repository.save(dailyQuestionBatch);
		return mapper.toDto(saved);
	}

	@Transactional(readOnly = true)
	public Optional<DailyQuestionBatchDto> findByUserIdxAndPracticeDate(Long userIdx, LocalDate practiceDate) {
		Optional<DailyQuestionBatch> dailyQuestionBatch = repository.findByUserIdxAndPracticeDate(userIdx,
			practiceDate);
		return dailyQuestionBatch.map(mapper::toDto);
	}

	@Transactional(readOnly = true)
	public List<DailyQuestionBatchDto> findAllByUserIdxAndPracticeDateBetween(Long userIdx, LocalDate startDate, LocalDate endDate) {
		List<DailyQuestionBatch> dailyQuestionBatches = repository.findAllByUserIdxAndPracticeDateBetween(
			userIdx, startDate, endDate);
		return mapper.getDtoList(dailyQuestionBatches);
	}
}
