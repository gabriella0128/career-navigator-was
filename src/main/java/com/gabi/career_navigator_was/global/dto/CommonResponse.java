package com.gabi.career_navigator_was.global.dto;

import lombok.Getter;

@Getter
public class CommonResponse<T> {
	private boolean success;
	private String code;
	private String message;
	private T data;

	public CommonResponse() {}

	public CommonResponse(boolean success, String code, String message, T data) {
		this.success = success;
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public static <T> CommonResponse<T> success(String successMessage, T data) {
		return new CommonResponse<>(true, "Success", successMessage, data);
	}

	public static <T> CommonResponse<T> failure(String errorCode, String message) {
		return new CommonResponse<T>(false, errorCode, message, null);
	}
}
