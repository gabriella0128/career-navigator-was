package com.gabi.career_navigator_was.global.config.auditor;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.gabi.career_navigator_was.global.code.HdrType;
import com.gabi.career_navigator_was.global.code.TokenStatus;
import com.gabi.career_navigator_was.global.util.JwtTokenUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<String> {
	private final JwtTokenUtil jwtTokenUtil;

	@Override
	public Optional<String> getCurrentAuditor() {
		Optional<String> accessTokenOpt = extractTokenFromHeader();
		if (accessTokenOpt.isPresent()) {
			String accessToken = accessTokenOpt.get();
			TokenStatus tokenStatus = jwtTokenUtil.validateToken(accessToken);
			if (!TokenStatus.INVALID.equals(tokenStatus)) {
				return Optional.of(jwtTokenUtil.getSubjectFromToken(accessToken));
			}
		}
		return Optional.empty();
	}

	public Optional<String> getAuditor(String currentValue) {
		Optional<String> tokenAuditor = getCurrentAuditor();
		if (tokenAuditor.isPresent()) {
			return tokenAuditor;
		}
		if (!Objects.isNull(currentValue) && !currentValue.isBlank()) {
			return Optional.of(currentValue);
		}
		return Optional.of("anonymous");
	}

	private Optional<String> extractTokenFromHeader() {

		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (Objects.isNull(attributes)) {
			return Optional.empty();
		}

		HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
		String bearerToken = request.getHeader(HdrType.AUTH_HEADER.getHdrName());
		if (!jwtTokenUtil.isValidTokenFormat(bearerToken)) {
			return Optional.empty();
		}
		return Optional.of(bearerToken.substring(7));

	}
}
