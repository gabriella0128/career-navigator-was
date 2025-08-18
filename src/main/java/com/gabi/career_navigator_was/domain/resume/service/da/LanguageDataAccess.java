package com.gabi.career_navigator_was.domain.resume.service.da;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabi.career_navigator_was.domain.resume.dto.base.LanguageDto;
import com.gabi.career_navigator_was.domain.resume.entity.Language;
import com.gabi.career_navigator_was.domain.resume.mapper.LanguageMapper;
import com.gabi.career_navigator_was.domain.resume.repository.LanguageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LanguageDataAccess {
	private final LanguageRepository repository;
	private final LanguageMapper mapper;

	@Transactional
	public LanguageDto save(LanguageDto languageDto) {
		Language language = mapper.toEntity(languageDto);
		Language saved = repository.save(language);
		return mapper.toDto(saved);
	}

	@Transactional
	public List<LanguageDto> saveAll(List<LanguageDto> languageDtos) {
		List<Language> languages = mapper.getEntityList(languageDtos);
		List<Language> saved = repository.saveAll(languages);
		return mapper.getDtoList(saved);
	}

	@Transactional(readOnly = true)
	public List<LanguageDto> findAllByResumeIdx(Long resumeIdx) {
		List<Language> languages = repository.findAllByResumeIdx(resumeIdx);
		return mapper.getDtoList(languages);
	}

}
