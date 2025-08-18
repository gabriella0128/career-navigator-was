package com.gabi.career_navigator_was.global.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
	INVALID_PARAMETER(400, "CARNAV-E1001", "잘못된 입력 값 입니다.");
	private int status;
	private String errorCode;
	private String message;

	public int getStatus() {
		return this.status;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public String getMessage() {
		return this.message;
	}
}
