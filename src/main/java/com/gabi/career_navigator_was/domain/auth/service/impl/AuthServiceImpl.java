package com.gabi.career_navigator_was.domain.auth.service.impl;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabi.career_navigator_was.domain.auth.dto.base.BlackTokenDto;
import com.gabi.career_navigator_was.domain.auth.dto.base.TokenDto;
import com.gabi.career_navigator_was.domain.auth.dto.request.PageAuthReq;
import com.gabi.career_navigator_was.domain.auth.dto.response.ReissueRes;
import com.gabi.career_navigator_was.domain.auth.service.AuthService;
import com.gabi.career_navigator_was.domain.auth.service.da.TokenDataAccess;
import com.gabi.career_navigator_was.domain.user.dto.base.UserInfoDto;
import com.gabi.career_navigator_was.domain.user.service.da.UserInfoDataAccess;
import com.gabi.career_navigator_was.global.code.CarnavCustomErrorCode;
import com.gabi.career_navigator_was.global.code.TokenStatus;
import com.gabi.career_navigator_was.global.dto.CommonResponse;
import com.gabi.career_navigator_was.global.exception.CarnavCustomException;
import com.gabi.career_navigator_was.global.util.JwtTokenUtil;
import com.gabi.career_navigator_was.domain.auth.dto.response.LoginRes;
import com.gabi.career_navigator_was.domain.auth.dto.request.LoginReq;
import com.gabi.career_navigator_was.domain.auth.repository.TokenRepository;
import com.gabi.career_navigator_was.domain.auth.service.da.BlackTokenDataAccess;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserInfoDataAccess userInfoDataAccess;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenUtil jwtTokenUtil;
	private final TokenDataAccess tokenDataAccess;
	private final ObjectMapper objectMapper;
	private final TokenRepository tokenRepository;
	private final BlackTokenDataAccess blackTokenDataAccess;

	@Value("${jwt.expriation.refreshToken}")
	private Long refreshTokenExpiration;

	@Override
	public CommonResponse<LoginRes> loginUser(LoginReq body) {
		Optional<UserInfoDto> userInfoDtoOpt = userInfoDataAccess.findByUserId(body.userId());
		if (userInfoDtoOpt.isEmpty()) {
			return CommonResponse.failure(
				CarnavCustomErrorCode.NOT_FOUND_USER.getErrorCode(),
				CarnavCustomErrorCode.NOT_FOUND_USER.getMessage());
		}
		UserInfoDto userInfoDto = userInfoDtoOpt.get();

		if (!passwordEncoder.matches(body.userPasswd(), userInfoDto.userPasswd())) {
			return CommonResponse.failure(
				CarnavCustomErrorCode.INVALID_PASSWORD.getErrorCode(),
				CarnavCustomErrorCode.INVALID_PASSWORD.getMessage());
		}

		Set<String> roles = new HashSet<>();
		roles.add("USER");

		Map<String, Object> claims = Map.of("roles", roles);

		String accessToken = jwtTokenUtil.createAccessToken(userInfoDto.userId(), claims);
		String refreshToken = jwtTokenUtil.createRefreshToken(userInfoDto.userId(), claims);
		tokenDataAccess.save(TokenDto.builder()
			.userId(userInfoDto.userId())
			.refreshToken(refreshToken)
			.createdAt(LocalDateTime.now())
			.expiration(refreshTokenExpiration).build());

		UserInfoDto updatedUserInfoDto = userInfoDto.toBuilder()
			.lastLoginDt(LocalDateTime.now())
			.modifyId(body.userId())
			.build();

		userInfoDataAccess.save(updatedUserInfoDto);

		LoginRes loginResponse = LoginRes.builder()
			.accessToken(accessToken)
			.userId(userInfoDto.userId())
			.build();

		return CommonResponse.success("로그인 성공", loginResponse);

	}

	@Override
	public CommonResponse<Void> logoutUser() {
		String userId = jwtTokenUtil.getUserIdFromToken();
		String accessToken = jwtTokenUtil.extractTokenFromHeader();

		tokenRepository.deleteById(userId);

		Long remainingTime = jwtTokenUtil.getRemainingTime(accessToken);

		if(remainingTime > 0) {
			blackTokenDataAccess.save(BlackTokenDto.builder()
					.token(accessToken)
					.blacklistedAt(LocalDateTime.now())
					.timeToLive(TimeUnit.MICROSECONDS.toSeconds(remainingTime))
				.build());
		}

		return CommonResponse.success("로그아웃 성공", null);
	}

	@Override
	public CommonResponse<ReissueRes> reissueToken() {
		String accessToken = jwtTokenUtil.extractTokenFromHeader();
		TokenStatus accessTokenStatus = jwtTokenUtil.validateToken(accessToken);
		if (TokenStatus.INVALID.equals(accessTokenStatus)) {
			throw new CarnavCustomException(CarnavCustomErrorCode.INVALID_TOKEN);
		}
		String userId = jwtTokenUtil.getSubjectFromToken(accessToken);

		TokenDto refreshTokenDto = tokenDataAccess.findByUserId(userId);
		TokenStatus refreshTokenStatus = jwtTokenUtil.validateToken(refreshTokenDto.refreshToken());

		if (TokenStatus.EXPIRED.equals(refreshTokenStatus)) {
			throw new CarnavCustomException(CarnavCustomErrorCode.EXPIRED_TOKEN);
		}
		Set<String> roles = objectMapper.convertValue(jwtTokenUtil.getClaims(refreshTokenDto.refreshToken()).get("roles"),
			new TypeReference<>() {
			});
		Map<String, Object> claims = Map.of("roles", roles);

		String reissuedAccessToken = jwtTokenUtil.createAccessToken(userId, claims);

		String refreshToken = jwtTokenUtil.createRefreshToken(userId, claims);
		tokenDataAccess.save(TokenDto.builder()
			.userId(userId)
			.refreshToken(refreshToken)
			.createdAt(LocalDateTime.now())
			.expiration(refreshTokenExpiration).build());

		ReissueRes returnData = ReissueRes.builder().accessToken(reissuedAccessToken).build();
		return CommonResponse.success("토큰 재발급 성공", returnData);
	}

	@Override
	public CommonResponse<Void> authenticatePage(PageAuthReq body) {
		jwtTokenUtil.checkTokenAuthentication();
		return CommonResponse.success("페이지 인증 성공", null);
	}
}
