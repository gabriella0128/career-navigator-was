package com.gabi.career_navigator_was.domain.resume.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabi.career_navigator_was.domain.resume.entity.Resume;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
	boolean existsByUserIdx(Long userIdx);

	Optional<Resume> findByResumeIdx(Long resumeIdx);

	List<Resume> findByUserIdx(Long userIdx);
}
