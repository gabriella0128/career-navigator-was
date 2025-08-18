package com.gabi.career_navigator_was.global.exception;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.gabi.career_navigator_was.global.dto.ErrorResponse;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
	@ExceptionHandler(value = {CarnavCustomException.class})
	protected ResponseEntity<ErrorResponse> handleCreCustomException(CarnavCustomException re,
		HttpServletResponse response) {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		return ErrorResponse.toResponseEntity(re.getErrorCode(), null);
	}
}
