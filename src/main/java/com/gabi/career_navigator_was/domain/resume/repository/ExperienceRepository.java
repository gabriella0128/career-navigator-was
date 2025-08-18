package com.gabi.career_navigator_was.domain.resume.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabi.career_navigator_was.domain.resume.entity.Experience;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
	List<Experience> findAllByResumeIdx(Long resumeIdx);
}
