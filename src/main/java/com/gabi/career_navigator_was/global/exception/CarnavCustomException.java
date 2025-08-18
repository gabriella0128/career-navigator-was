package com.gabi.career_navigator_was.global.exception;

import com.gabi.career_navigator_was.global.code.CarnavCustomErrorCode;

import lombok.Getter;

@Getter
public class CarnavCustomException extends RuntimeException {
	private static final long serialVersionUID = 1460113520651918298L;

	private final CarnavCustomErrorCode errorCode;

	public CarnavCustomException(CarnavCustomErrorCode errorCode) {
		this.errorCode = errorCode;
	}
}
