package com.gabi.career_navigator_was.domain.learning.service.da;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabi.career_navigator_was.domain.learning.dto.base.LearningPlanDto;
import com.gabi.career_navigator_was.domain.learning.entity.LearningPlan;
import com.gabi.career_navigator_was.domain.learning.mapper.LearningPlanMapper;
import com.gabi.career_navigator_was.domain.learning.repository.LearningPlanRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LearningPlanDataAccess {
	private final LearningPlanRepository repository;
	private final LearningPlanMapper mapper;

	@Transactional
	public LearningPlanDto save(LearningPlanDto learningPlanDto) {
		LearningPlan learningPlan = mapper.toEntity(learningPlanDto);
		LearningPlan saved = repository.save(learningPlan);
		return mapper.toDto(saved);
	}

	@Transactional
	public List<LearningPlanDto> saveAll(List<LearningPlanDto> learningPlanDtos) {
		List<LearningPlan> learningPlans = mapper.getEntityList(learningPlanDtos);
		List<LearningPlan> saved = repository.saveAll(learningPlans);
		return mapper.getDtoList(saved);
	}

	@Transactional(readOnly = true)
	public boolean existsByUserIdxAndPeriodStart(Long userIdx, LocalDate periodStart) {
		return repository.existsByUserIdxAndPeriodStart(userIdx, periodStart);
	}

	@Transactional(readOnly = true)
	public Optional<LearningPlanDto> findByUserIdxAndPeriodStart(Long userIdx, LocalDate periodStart) {
		Optional<LearningPlan> learningPlanOpt = repository.findByUserIdxAndPeriodStart(userIdx, periodStart);
		return learningPlanOpt.map(mapper::toDto);
	}
}
