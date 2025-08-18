package com.gabi.career_navigator_was.domain.interview.service.da;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabi.career_navigator_was.domain.interview.dto.base.DailyAnswerEvalDto;
import com.gabi.career_navigator_was.domain.interview.entity.DailyAnswerEval;
import com.gabi.career_navigator_was.domain.interview.mapper.DailyAnswerEvalMapper;
import com.gabi.career_navigator_was.domain.interview.repository.DailyAnswerEvalRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DailyAnswerEvalDataAccess {
	private final DailyAnswerEvalRepository repository;
	private final DailyAnswerEvalMapper mapper;

	@Transactional
	public DailyAnswerEvalDto save(DailyAnswerEvalDto dailyAnswerEvalDto) {
		DailyAnswerEval dailyAnswerEval = this.mapper.toEntity(dailyAnswerEvalDto);
		DailyAnswerEval saved = this.repository.save(dailyAnswerEval);
		return this.mapper.toDto(saved);
	}

	@Transactional(readOnly = true)
	public Optional<DailyAnswerEvalDto> findByAnswerIdx(Long answerIdx) {
		return this.repository.findByAnswerIdx(answerIdx).map(mapper::toDto);
	}

	@Transactional(readOnly = true)
	public List<DailyAnswerEvalDto> findAllByUserIdxAndEvaluatedAtBetween(Long userIdx, LocalDateTime start, LocalDateTime end) {
		List<DailyAnswerEval> allEvalDtos = repository.findAllByUserIdxAndEvaluatedAtBetween(userIdx, start, end);
		return mapper.getDtoList(allEvalDtos);
	}

	@Transactional(readOnly = true)
	public List<DailyAnswerEvalDto> findAllByUserIdx(Long userIdx) {
		List<DailyAnswerEval> allByUserIdx = repository.findAllByUserIdx(userIdx);
		return mapper.getDtoList(allByUserIdx);
	}

	@Transactional(readOnly = true)
	public List<DailyAnswerEvalDto> findAllByAnswerIdxIn(List<Long> answerIdxList) {
		List<DailyAnswerEval> allByAnswerIdxIn = repository.findAllByAnswerIdxIn(answerIdxList);
		return mapper.getDtoList(allByAnswerIdxIn);
	}
}
