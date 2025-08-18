package com.gabi.career_navigator_was.domain.resume.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabi.career_navigator_was.domain.resume.entity.Certificate;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
	List<Certificate> findAllByResumeIdx(Long resumeIdx);
}
