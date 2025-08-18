package com.gabi.career_navigator_was.domain.interview.service.da;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabi.career_navigator_was.domain.interview.dto.base.DailyQuestionDto;
import com.gabi.career_navigator_was.domain.interview.entity.DailyQuestion;
import com.gabi.career_navigator_was.domain.interview.mapper.DailyQuestionMapper;
import com.gabi.career_navigator_was.domain.interview.repository.DailyQuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DailyQuestionDataAccess {
	private final DailyQuestionRepository repository;
	private final DailyQuestionMapper mapper;

	@Transactional
	public DailyQuestionDto save(DailyQuestionDto dailyQuestionDto) {
		DailyQuestion dailyQuestion = mapper.toEntity(dailyQuestionDto);
		DailyQuestion saved = repository.save(dailyQuestion);
		return mapper.toDto(saved);
	}

	@Transactional
	public List<DailyQuestionDto> saveAll(List<DailyQuestionDto> dailyQuestionDtos) {
		List<DailyQuestion> dailyQuestions = mapper.getEntityList(dailyQuestionDtos);
		List<DailyQuestion> saved = repository.saveAll(dailyQuestions);
		return mapper.getDtoList(saved);
	}

	@Transactional(readOnly = true)
	public List<DailyQuestionDto> findByBatchIdx(Long batchIdx) {
		List<DailyQuestion> dailyQuestions = repository.findByBatchIdx(batchIdx);
		return mapper.getDtoList(dailyQuestions);
	}

	@Transactional(readOnly = true)
	public List<DailyQuestionDto> findByBatchIdxIn(List<Long> batchIdx) {
		List<DailyQuestion> dailyQuestions = repository.findByBatchIdxIn(batchIdx);
		return mapper.getDtoList(dailyQuestions);
	}

	@Transactional(readOnly = true)
	public Optional<DailyQuestionDto> findByQuestionIdx(Long questionIdx) {
		Optional<DailyQuestion> dailyQuestionOpt = repository.findByQuestionIdx(questionIdx);
		return dailyQuestionOpt.map(mapper::toDto);
	}

	@Transactional(readOnly = true)
	public List<DailyQuestionDto> findAllByQuestionIdxIn(List<Long> questionIdx) {
		List<DailyQuestion> allByQuestionIdxIn = repository.findAllByQuestionIdxIn(questionIdx);
		return mapper.getDtoList(allByQuestionIdxIn);
	}
}
