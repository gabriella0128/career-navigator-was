package com.gabi.career_navigator_was.domain.resume.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabi.career_navigator_was.domain.resume.entity.Language;

public interface LanguageRepository extends JpaRepository<Language, Long> {
	List<Language> findAllByResumeIdx(Long resumeIdx);
}
