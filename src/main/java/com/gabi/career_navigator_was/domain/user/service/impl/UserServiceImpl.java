package com.gabi.career_navigator_was.domain.user.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gabi.career_navigator_was.domain.user.dto.base.UserInfoDto;
import com.gabi.career_navigator_was.domain.user.dto.request.IdCheckReq;
import com.gabi.career_navigator_was.domain.user.dto.request.InsertUserReq;
import com.gabi.career_navigator_was.domain.user.dto.response.IdCheckRes;
import com.gabi.career_navigator_was.domain.user.dto.response.UserInfoRes;
import com.gabi.career_navigator_was.domain.user.service.UserService;
import com.gabi.career_navigator_was.domain.user.service.da.UserInfoDataAccess;
import com.gabi.career_navigator_was.global.code.CarnavCustomErrorCode;
import com.gabi.career_navigator_was.global.dto.CommonResponse;
import com.gabi.career_navigator_was.global.exception.CarnavCustomException;
import com.gabi.career_navigator_was.global.util.JwtTokenUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserInfoDataAccess userInfoDataAccess;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenUtil jwtTokenUtil;

	@Override
	public CommonResponse<IdCheckRes> checkIdDuplication(IdCheckReq body) {
		boolean isDuplicated = userInfoDataAccess.existsByUserId(body.userId());
		IdCheckRes returnData = isDuplicated
			? new IdCheckRes(true)
			: new IdCheckRes(false);

		return CommonResponse.success("중복 여부 조회 성공", returnData);
	}

	@Override
	public CommonResponse<Void> insertUser(InsertUserReq body) {
		String encryptedPassword = passwordEncoder.encode(body.userPasswd());

		UserInfoDto userInfoDto = UserInfoDto.builder()
			.userId(body.userId())
			.userName(body.userName())
			.userPasswd(encryptedPassword)
			.userEmail(body.userEmail())
			.createId(body.userId())
			.modifyId(body.userId())
			.build();
		userInfoDataAccess.save(userInfoDto);

		return CommonResponse.success("유저 가입 성공", null);
	}

	@Override
	public CommonResponse<UserInfoRes> retrieveUser() {
		Long userIdx = currentUserIdx();
		UserInfoDto userInfoDto = userInfoDataAccess.findByUserIdx(userIdx)
			.orElseThrow(() -> new CarnavCustomException(CarnavCustomErrorCode.NOT_FOUND_USER));

		UserInfoRes returnData = UserInfoRes.builder()
				.userIdx(userInfoDto.userIdx())
				.userId(userInfoDto.userId())
				.userName(userInfoDto.userName())
				.userEmail(userInfoDto.userEmail())
				.build();

		return CommonResponse.success("유저 정보 조회 성공", returnData);
	}

	private Long currentUserIdx() {
		String userId = jwtTokenUtil.getUserIdFromToken();
		return userInfoDataAccess.findByUserId(userId)
			.orElseThrow(() -> new CarnavCustomException(CarnavCustomErrorCode.NOT_FOUND_USER))
			.userIdx();
	}
}
