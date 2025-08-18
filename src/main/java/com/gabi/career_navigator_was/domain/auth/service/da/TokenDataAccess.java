package com.gabi.career_navigator_was.domain.auth.service.da;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gabi.career_navigator_was.domain.auth.dto.base.TokenDto;
import com.gabi.career_navigator_was.domain.auth.entity.Token;
import com.gabi.career_navigator_was.domain.auth.mapper.TokenMapper;
import com.gabi.career_navigator_was.domain.auth.repository.TokenRepository;
import com.gabi.career_navigator_was.global.code.CarnavCustomErrorCode;
import com.gabi.career_navigator_was.global.exception.CarnavCustomException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenDataAccess {
	private final TokenRepository repository;
	private final TokenMapper mapper;

	@Transactional
	public void save(TokenDto dto) {
		repository.save(mapper.toEntity(dto));
	}

	@Transactional(readOnly = true)
	public TokenDto findByUserId(String userId) {
		Token entity = repository.findById(userId)
			.orElseThrow(() -> new CarnavCustomException(CarnavCustomErrorCode.NOT_FOUND_RESOURCE));
		return mapper.toDto(entity);
	}
}
