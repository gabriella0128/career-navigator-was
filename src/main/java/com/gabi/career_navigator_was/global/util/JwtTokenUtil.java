package com.gabi.career_navigator_was.global.util;

import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabi.career_navigator_was.global.code.CarnavCustomErrorCode;
import com.gabi.career_navigator_was.global.code.HdrType;
import com.gabi.career_navigator_was.global.code.TokenStatus;
import com.gabi.career_navigator_was.global.exception.CarnavCustomException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenUtil {
	@Value("${jwt.secret.key}")
	private String secretKeyString; // 비밀키 문자열

	@Value("${jwt.expiration.accessToken}")
	private Long accessTokenExpiration; // 엑세스 토큰 만료시간

	@Value("${jwt.expriation.refreshToken}")
	private Long refreshTokenExpiration;// 리프레시 토큰 만료시간

	private SecretKey secretKey; // 비밀키

	@PostConstruct
	private void init() {
		byte[] keyBytes = Base64.getDecoder().decode(secretKeyString);
		secretKey = Keys.hmacShaKeyFor(keyBytes);
	}

	private String createToken(String subject, Map<String, Object> claims, Long expirationTime) {
		return Jwts.builder()
			.subject(subject) // 인증하려는 주체
			.claims(claims) // 토큰에 담는 기타 권한 정보
			.issuedAt(new Date()) // 발행 시점
			.expiration(new Date(System.currentTimeMillis() + expirationTime)) // 만료 시간
			.signWith(secretKey) // 서명
			.compact(); // 토큰 생성
	}

	public String createAccessToken(String subject, Map<String, Object> claims) {
		return createToken(subject, claims, accessTokenExpiration);
	}

	public String createRefreshToken(String subject, Map<String, Object> claims) {
		return createToken(subject, claims, refreshTokenExpiration);
	}


	public TokenStatus validateToken(String token) {
		try {
			Jws<Claims> claimsJws = Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token);

			Date expiration = claimsJws.getPayload().getExpiration();
			return expiration.after(new Date()) ? TokenStatus.VALID : TokenStatus.EXPIRED;
		} catch (ExpiredJwtException e) {
			return TokenStatus.EXPIRED;
		} catch (Exception e) {
			log.info("Exception occurred while validating token", e );
			return TokenStatus.INVALID;
		}
	}

	/**
	 * 만료 여부와 상관없이 토큰에서 정보를 추출하는 method
	 */
	public Claims getClaims(String token) {
		try {
			return Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token)
				.getPayload();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		} catch (Exception e) {
			throw new CarnavCustomException(CarnavCustomErrorCode.INVALID_TOKEN);
		}
	}

	/**
	 * 토큰에서 subject를 추출하는 method
	 */
	public String getSubjectFromToken(String token) {
		return getClaims(token).getSubject();
	}

	/**
	 * header에서 token을 추출하는 method
	 */
	public String extractTokenFromHeader() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

		String bearerToken = request.getHeader(HdrType.AUTH_HEADER.getHdrName());
		if (!isValidTokenFormat(bearerToken)) {
			throw new CarnavCustomException(CarnavCustomErrorCode.INVALID_TOKEN);
		}
		return bearerToken.substring(7);
	}

	/**
	 * 토큰 유효성을 검증하고 유효할 시 권한 정보를 반환하는 method
	 */
	public Set<String> checkTokenAuthentication() {
		String accessToken = extractTokenFromHeader();
		TokenStatus tokenStatus = validateToken(accessToken);

		Set<String> roles = new HashSet<>();

		switch (tokenStatus) {
			case VALID:
				log.info("Authentication successful");
				ObjectMapper objectMapper = new ObjectMapper();

				roles = objectMapper.convertValue(
					getClaims(accessToken).get("roles"),
					new TypeReference<Set<String>>() {}
				);
				break;
			case EXPIRED:
				throw new CarnavCustomException(CarnavCustomErrorCode.EXPIRED_TOKEN);
			case INVALID:
				throw new CarnavCustomException(CarnavCustomErrorCode.INVALID_TOKEN);
		}
		return roles;
	}

	/**
	 * token에서 userId를 추출하는 method
	 */
	public String getUserIdFromToken() {

		String accessToken = extractTokenFromHeader();
		TokenStatus tokenStatus = validateToken(accessToken);

		if (TokenStatus.INVALID.equals(tokenStatus)) {
			throw new CarnavCustomException(CarnavCustomErrorCode.INVALID_TOKEN);
		}
		return getSubjectFromToken(accessToken);

	}

	/**
	 * 토큰 포맷 검증 로직
	 */
	public boolean isValidTokenFormat(String bearerToken) {
		return !(Objects.isNull(bearerToken)
			|| !bearerToken.startsWith("Bearer ")
			|| bearerToken.substring(7).isEmpty()
			|| bearerToken.substring(7).equals("null"));
	}

	/**
	 * 토큰 남은 만료 시간 계산 로직
	 */
	public Long getRemainingTime(String token) {
		try {
			Claims claims = Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token)
				.getPayload();

			Date expiration = claims.getExpiration();
			Date now = new Date();

			return Math.max(0, expiration.getTime() - now.getTime());
		} catch (Exception e) {
			return 0L; // 토큰이 만료
		}
	}

}
