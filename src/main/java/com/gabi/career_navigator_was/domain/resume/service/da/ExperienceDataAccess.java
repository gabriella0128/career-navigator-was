package com.gabi.career_navigator_was.domain.resume.service.da;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabi.career_navigator_was.domain.resume.dto.base.ExperienceDto;
import com.gabi.career_navigator_was.domain.resume.entity.Experience;
import com.gabi.career_navigator_was.domain.resume.mapper.ExperienceMapper;
import com.gabi.career_navigator_was.domain.resume.repository.ExperienceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExperienceDataAccess {
	private final ExperienceRepository repository;
	private final ExperienceMapper mapper;

	@Transactional
	public ExperienceDto save(ExperienceDto experienceDto) {
		Experience experience = mapper.toEntity(experienceDto);
		Experience saved = repository.save(experience);
		return mapper.toDto(saved);
	}

	@Transactional
	public List<ExperienceDto> saveAll(List<ExperienceDto> experienceDtos) {
		List<Experience> experiences = mapper.getEntityList(experienceDtos);
		List<Experience> saved = repository.saveAll(experiences);
		return mapper.getDtoList(saved);
	}

	@Transactional(readOnly = true)
	public List<ExperienceDto> findAllByResumeIdx(Long resumeIdx) {
		List<Experience> experiences = repository.findAllByResumeIdx(resumeIdx);
		return mapper.getDtoList(experiences);
	}
}
