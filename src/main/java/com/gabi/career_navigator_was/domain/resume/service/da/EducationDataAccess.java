package com.gabi.career_navigator_was.domain.resume.service.da;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabi.career_navigator_was.domain.resume.dto.base.EducationDto;
import com.gabi.career_navigator_was.domain.resume.entity.Education;
import com.gabi.career_navigator_was.domain.resume.mapper.EducationMapper;
import com.gabi.career_navigator_was.domain.resume.repository.EducationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EducationDataAccess {
	private final EducationRepository repository;
	private final EducationMapper mapper;

	@Transactional
	public EducationDto save(EducationDto educationDto) {
		Education education = mapper.toEntity(educationDto);
		Education saved = repository.save(education);
		return mapper.toDto(saved);
	}

	@Transactional
	public List<EducationDto> saveAll(List<EducationDto> educationDtos) {
		List<Education> educations = mapper.getEntityList(educationDtos);
		List<Education> saved = repository.saveAll(educations);
		return mapper.getDtoList(saved);
	}

	@Transactional(readOnly = true)
	public List<EducationDto> findAllByResumeIdx(Long resumeIdx) {
		List<Education> educations = repository.findAllByResumeIdx(resumeIdx);
		return mapper.getDtoList(educations);
	}
}
