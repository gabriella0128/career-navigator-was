package com.gabi.career_navigator_was.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabi.career_navigator_was.domain.user.entity.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
	boolean existsByUserId(String userId);

	Optional<UserInfo> findByUserId(String userId);

	Optional<UserInfo> findByUserIdx(Long userIdx);
}
