package com.gabi.career_navigator_was.domain.auth.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.context.annotation.Primary;

import com.gabi.career_navigator_was.domain.auth.dto.base.BlackTokenDto;
import com.gabi.career_navigator_was.domain.auth.entity.BlackToken;
import com.gabi.career_navigator_was.global.mapper.GenericMapper;

@Primary
@Mapper(
	componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface BlackTokenMapper extends GenericMapper<BlackTokenDto, BlackToken> {
	@Override
	BlackTokenDto toDto(BlackToken entity);

	@Override
	BlackToken toEntity(BlackTokenDto dto);

	@Override
	List<BlackTokenDto> getDtoList(List<BlackToken> entityList);
}
