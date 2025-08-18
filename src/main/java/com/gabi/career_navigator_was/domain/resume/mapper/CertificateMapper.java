package com.gabi.career_navigator_was.domain.resume.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.gabi.career_navigator_was.domain.resume.dto.base.CertificateDto;
import com.gabi.career_navigator_was.domain.resume.entity.Certificate;
import com.gabi.career_navigator_was.global.mapper.GenericMapper;

@Mapper(
	componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CertificateMapper extends GenericMapper<CertificateDto, Certificate> {
	@Override
	CertificateDto toDto(Certificate entity);

	@Override
	Certificate toEntity(CertificateDto dto);

	@Override
	List<CertificateDto> getDtoList(List<Certificate> entityList);

	@Override
	List<Certificate> getEntityList(List<CertificateDto> dtoList);
}
