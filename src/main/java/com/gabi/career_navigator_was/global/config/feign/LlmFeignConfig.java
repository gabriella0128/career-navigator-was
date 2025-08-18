package com.gabi.career_navigator_was.global.config.feign;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.gabi.career_navigator_was.global.code.HdrType;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class LlmFeignConfig {

	@Value("${feign.llm.api-key:}")
	private String defaultApiKey;

	@Bean
	public RequestInterceptor llmRequestInterceptor() {
		return requestTemplate -> {
			ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
			String apiKey = null;
			if (!Objects.isNull(attributes)) {
				HttpServletRequest request = attributes.getRequest();
				apiKey = request.getHeader(HdrType.LLM_API_KEY_HEADER.getHdrName());
			}
			if (Objects.isNull(apiKey) || apiKey.isEmpty()) {
				apiKey = defaultApiKey;
			}
			requestTemplate.header(HdrType.LLM_API_KEY_HEADER.getHdrName(), apiKey);
		};
	}
}
