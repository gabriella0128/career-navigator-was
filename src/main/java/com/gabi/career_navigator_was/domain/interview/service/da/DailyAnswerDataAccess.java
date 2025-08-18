package com.gabi.career_navigator_was.domain.interview.service.da;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabi.career_navigator_was.domain.interview.dto.base.DailyAnswerDto;
import com.gabi.career_navigator_was.domain.interview.entity.DailyAnswer;
import com.gabi.career_navigator_was.domain.interview.mapper.DailyAnswerMapper;
import com.gabi.career_navigator_was.domain.interview.repository.DailyAnswerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DailyAnswerDataAccess {

	private final DailyAnswerRepository repository;
	private final DailyAnswerMapper mapper;

	@Transactional
	public DailyAnswerDto save(DailyAnswerDto dailyAnswerDto) {
		DailyAnswer dailyAnswer = mapper.toEntity(dailyAnswerDto);
		DailyAnswer saved = repository.save(dailyAnswer);
		return mapper.toDto(saved);
	}

	@Transactional(readOnly = true)
	public boolean existsByQuestionIdx(Long questionIdx) {
		return repository.existsByQuestionIdx(questionIdx);
	}

	@Transactional(readOnly = true)
	public Optional<DailyAnswerDto> findByQuestionIdx(Long questionIdx) {
		return repository.findByQuestionIdx(questionIdx).map(mapper::toDto);
	}

	@Transactional(readOnly = true)
	public int countByUserIdxAndSubmittedAtBetween(Long userIdx, LocalDateTime start, LocalDateTime end) {
		return repository.countByUserIdxAndSubmittedAtBetween(userIdx, start, end);
	}

	@Transactional(readOnly = true)
	public List<LocalDateTime> findSubmittedTime(Long userIdx, LocalDateTime start, LocalDateTime end) {
		return repository.findSubmitTimes(userIdx, start, end);
	}

	@Transactional(readOnly = true)
	public List<DailyAnswerDto> findAllByUserIdxAndSubmittedAtBetween(Long userIdx, LocalDateTime start, LocalDateTime end) {
		List<DailyAnswer> allDailyAnswers = repository.findAllByUserIdxAndSubmittedAtBetween(userIdx,
			start, end);
		return mapper.getDtoList(allDailyAnswers);
	}
}
