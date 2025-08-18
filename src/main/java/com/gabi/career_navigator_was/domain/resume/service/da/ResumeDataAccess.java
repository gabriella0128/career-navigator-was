package com.gabi.career_navigator_was.domain.resume.service.da;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabi.career_navigator_was.domain.resume.dto.base.ResumeDto;
import com.gabi.career_navigator_was.domain.resume.entity.Resume;
import com.gabi.career_navigator_was.domain.resume.mapper.ResumeMapper;
import com.gabi.career_navigator_was.domain.resume.repository.ResumeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResumeDataAccess {
	private final ResumeRepository repository;
	private final ResumeMapper mapper;

	@Transactional
	public ResumeDto save(ResumeDto resumeDto) {
		Resume resume = mapper.toEntity(resumeDto);
		Resume saved = repository.save(resume);
		return mapper.toDto(saved);
	}

	@Transactional(readOnly = true)
	public boolean existsByUserIdx(Long userIdx) {
		return repository.existsByUserIdx(userIdx);
	}

	@Transactional(readOnly=true)
	public Optional<ResumeDto> findByResumeIdx(Long resumeIdx) {
		return repository.findByResumeIdx(resumeIdx).map(mapper::toDto);
	}

	@Transactional(readOnly = true)
	public List<ResumeDto> findAllByUserIdx(Long userIdx) {
		List<Resume> resumes = repository.findByUserIdx(userIdx);
		return mapper.getDtoList(resumes);
	}
}
