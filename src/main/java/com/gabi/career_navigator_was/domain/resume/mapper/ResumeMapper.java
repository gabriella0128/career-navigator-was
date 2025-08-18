package com.gabi.career_navigator_was.domain.resume.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.gabi.career_navigator_was.domain.resume.dto.base.ResumeDto;
import com.gabi.career_navigator_was.domain.resume.entity.Resume;
import com.gabi.career_navigator_was.global.mapper.GenericMapper;
@Mapper(
	componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ResumeMapper extends GenericMapper<ResumeDto, Resume> {
	@Override
	ResumeDto toDto(Resume entity);

	@Override
	Resume toEntity(ResumeDto dto);

	@Override
	List<ResumeDto> getDtoList(List<Resume> entityList);

	@Override
	List<Resume> getEntityList(List<ResumeDto> dtoList);
}
