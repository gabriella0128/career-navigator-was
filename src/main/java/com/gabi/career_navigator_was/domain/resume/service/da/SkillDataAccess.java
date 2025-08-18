package com.gabi.career_navigator_was.domain.resume.service.da;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabi.career_navigator_was.domain.resume.dto.base.SkillDto;
import com.gabi.career_navigator_was.domain.resume.entity.Skill;
import com.gabi.career_navigator_was.domain.resume.mapper.SkillMapper;
import com.gabi.career_navigator_was.domain.resume.repository.SkillRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SkillDataAccess {
	private final SkillRepository repository;
	private final SkillMapper mapper;

	@Transactional
	public SkillDto save(SkillDto skillDto) {
		Skill skill = mapper.toEntity(skillDto);
		Skill saved = repository.save(skill);
		return mapper.toDto(saved);
	}

	@Transactional
	public List<SkillDto> saveAll(List<SkillDto> skillDtos) {
		List<Skill> skills = mapper.getEntityList(skillDtos);
		List<Skill> saved = repository.saveAll(skills);
		return mapper.getDtoList(saved);
	}

	@Transactional(readOnly = true)
	public List<SkillDto> findAllByResumeIdx(Long resumeIdx) {
		List<Skill> skills = repository.findAllByResumeIdx(resumeIdx);
		return mapper.getDtoList(skills);
	}

}
