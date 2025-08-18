package com.gabi.career_navigator_was.domain.auth.service.da;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabi.career_navigator_was.domain.auth.dto.base.BlackTokenDto;
import com.gabi.career_navigator_was.domain.auth.mapper.BlackTokenMapper;
import com.gabi.career_navigator_was.domain.auth.repository.BlackTokenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlackTokenDataAccess {
	private final BlackTokenRepository repository;
	private final BlackTokenMapper mapper;

	@Transactional
	public void save(BlackTokenDto dto) {
		repository.save(mapper.toEntity(dto));
	}
}
