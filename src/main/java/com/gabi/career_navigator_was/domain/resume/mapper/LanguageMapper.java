package com.gabi.career_navigator_was.domain.resume.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.gabi.career_navigator_was.domain.resume.dto.base.LanguageDto;
import com.gabi.career_navigator_was.domain.resume.entity.Language;
import com.gabi.career_navigator_was.global.mapper.GenericMapper;

@Mapper(
	componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface LanguageMapper extends GenericMapper<LanguageDto, Language> {
	@Override
	LanguageDto toDto(Language entity);

	@Override
	Language toEntity(LanguageDto dto);

	@Override
	List<LanguageDto> getDtoList(List<Language> entityList);

	@Override
	List<Language> getEntityList(List<LanguageDto> dtoList);
}
