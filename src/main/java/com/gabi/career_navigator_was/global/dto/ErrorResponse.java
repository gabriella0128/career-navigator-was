package com.gabi.career_navigator_was.global.dto;

import java.util.Date;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gabi.career_navigator_was.global.code.CarnavCustomErrorCode;
import com.gabi.career_navigator_was.global.code.ErrorCode;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {
	private int status;
	private String errorCode;
	private String message;
	private Object data;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private final Date createdAt = new Date();

	public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode, Object args) {

		return ResponseEntity
			.status(errorCode.getStatus())
			.body(ErrorResponse.builder()
				.status(errorCode.getStatus())
				.errorCode(errorCode.getErrorCode())
				.message(errorCode.getMessage())
				.data(args)
				.build()
			);
	}

	public static ResponseEntity<ErrorResponse> toResponseEntity(CarnavCustomErrorCode errorCode, Object args) {

		return ResponseEntity
			.status(errorCode.getStatus())
			.body(ErrorResponse.builder()
				.status(errorCode.getStatus())
				.errorCode(errorCode.getErrorCode())
				.message(errorCode.getMessage())
				.data(args)
				.build()
			);
	}

	public static ResponseEntity<ErrorResponse> toResponseEntity(int status, String errorCode, String message, Object args) {

		return ResponseEntity
			.status(status)
			.body(ErrorResponse.builder()
				.status(status)
				.errorCode(errorCode)
				.message(message)
				.data(args)
				.build()
			);
	}
}
