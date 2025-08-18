package com.gabi.career_navigator_was.domain.resume.service.da;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabi.career_navigator_was.domain.resume.dto.base.CertificateDto;
import com.gabi.career_navigator_was.domain.resume.entity.Certificate;
import com.gabi.career_navigator_was.domain.resume.mapper.CertificateMapper;
import com.gabi.career_navigator_was.domain.resume.repository.CertificateRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CertificateDataAccess {
	private final CertificateRepository repository;
	private final CertificateMapper mapper;

	@Transactional
	public CertificateDto save(CertificateDto certificateDto) {
		Certificate certificate = mapper.toEntity(certificateDto);
		Certificate saved = repository.save(certificate);
		return mapper.toDto(saved);
	}

	@Transactional
	public List<CertificateDto> saveAll(List<CertificateDto> certificateDtos) {
		List<Certificate> certificates = mapper.getEntityList(certificateDtos);
		List<Certificate> saved = repository.saveAll(certificates);
		return mapper.getDtoList(saved);
	}

	@Transactional(readOnly = true)
	public List<CertificateDto> findAllByResumeIdx(Long resumeIdx) {
		List<Certificate> certificates = repository.findAllByResumeIdx(resumeIdx);
		return mapper.getDtoList(certificates);
	}
}
