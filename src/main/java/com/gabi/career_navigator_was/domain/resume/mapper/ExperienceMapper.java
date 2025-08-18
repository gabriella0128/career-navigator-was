package com.gabi.career_navigator_was.domain.resume.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.gabi.career_navigator_was.domain.resume.dto.base.ExperienceDto;
import com.gabi.career_navigator_was.domain.resume.entity.Experience;
import com.gabi.career_navigator_was.global.mapper.GenericMapper;

@Mapper(
	componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ExperienceMapper extends GenericMapper<ExperienceDto, Experience> {
	@Override
	ExperienceDto toDto(Experience entity);

	@Override
	Experience toEntity(ExperienceDto dto);

	@Override
	List<ExperienceDto> getDtoList(List<Experience> entityList);

	@Override
	List<Experience> getEntityList(List<ExperienceDto> dtoList);
}
