package com.gabi.career_navigator_was.domain.resume.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabi.career_navigator_was.domain.resume.entity.Education;

public interface EducationRepository extends JpaRepository<Education, Long> {
	List<Education> findAllByResumeIdx(Long resumeIdx);
}
