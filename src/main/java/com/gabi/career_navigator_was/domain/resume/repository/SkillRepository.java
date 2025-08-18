package com.gabi.career_navigator_was.domain.resume.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabi.career_navigator_was.domain.resume.entity.Skill;

public interface SkillRepository extends JpaRepository<Skill, Long> {
	List<Skill> findAllByResumeIdx(Long resumeIdx);
}
