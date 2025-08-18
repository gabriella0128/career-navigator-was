package com.gabi.career_navigator_was.domain.resume.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.gabi.career_navigator_was.domain.resume.dto.base.EducationDto;
import com.gabi.career_navigator_was.domain.resume.entity.Education;
import com.gabi.career_navigator_was.global.mapper.GenericMapper;

@Mapper(
	componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface EducationMapper  extends GenericMapper<EducationDto, Education> {
	@Override
	EducationDto toDto(Education entity);

	@Override
	Education toEntity(EducationDto dto);

	@Override
	List<EducationDto> getDtoList(List<Education> entityList);

	@Override
	List<Education> getEntityList(List<EducationDto> dtoList);
}
