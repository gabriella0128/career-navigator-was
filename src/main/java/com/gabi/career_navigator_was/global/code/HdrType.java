package com.gabi.career_navigator_was.global.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum HdrType {
	AUTH_HEADER("Authorization"), // 인증 헤더
	LLM_API_KEY_HEADER("Llm-Api-Key");
	private String hdrName; // 헤더 이름
}
