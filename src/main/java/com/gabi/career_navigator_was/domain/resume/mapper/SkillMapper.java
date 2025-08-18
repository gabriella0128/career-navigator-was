package com.gabi.career_navigator_was.domain.resume.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.gabi.career_navigator_was.domain.resume.dto.base.SkillDto;
import com.gabi.career_navigator_was.domain.resume.entity.Skill;
import com.gabi.career_navigator_was.global.mapper.GenericMapper;

@Mapper(
	componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface SkillMapper extends GenericMapper<SkillDto, Skill> {
	@Override
	SkillDto toDto(Skill entity);

	@Override
	Skill toEntity(SkillDto dto);

	@Override
	List<SkillDto> getDtoList(List<Skill> entityList);

	@Override
	List<Skill> getEntityList(List<SkillDto> dtoList);
}
