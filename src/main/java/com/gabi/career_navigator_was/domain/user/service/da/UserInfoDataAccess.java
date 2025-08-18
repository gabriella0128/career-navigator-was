package com.gabi.career_navigator_was.domain.user.service.da;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabi.career_navigator_was.domain.user.dto.base.UserInfoDto;
import com.gabi.career_navigator_was.domain.user.entity.UserInfo;
import com.gabi.career_navigator_was.domain.user.repository.UserInfoRepository;
import com.gabi.career_navigator_was.domain.user.mapper.UserInfoMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserInfoDataAccess {

	private final UserInfoRepository repository;
	private final UserInfoMapper mapper;

	@Transactional(readOnly = true)
	public boolean existsByUserId(String userId) {
		return repository.existsByUserId(userId);
	}

	@Transactional(readOnly = true)
	public Optional<UserInfoDto> findByUserId(String userId) {
		return repository.findByUserId(userId)
			.map(mapper::toDto);
	}

	@Transactional(readOnly = true)
	public Optional<UserInfoDto> findByUserIdx(Long userIdx) {
		return repository.findByUserIdx(userIdx).map(mapper::toDto);
	}

	@Transactional
	public UserInfoDto save(UserInfoDto userInfoDto) {
		UserInfo userInfo = mapper.toEntity(userInfoDto);
		UserInfo saved = repository.save(userInfo);
		return mapper.toDto(saved);
	}
}
