package com.gabi.career_navigator_was.domain.auth.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.context.annotation.Primary;

import com.gabi.career_navigator_was.domain.auth.dto.base.TokenDto;
import com.gabi.career_navigator_was.domain.auth.entity.Token;
import com.gabi.career_navigator_was.global.mapper.GenericMapper;

@Primary
@Mapper(
	componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface TokenMapper extends GenericMapper<TokenDto, Token> {
	TokenDto toDto(Token entity);

	@Override
	Token toEntity(TokenDto dto);

	@Override
	List<TokenDto> getDtoList(List<Token> entityList);
}
