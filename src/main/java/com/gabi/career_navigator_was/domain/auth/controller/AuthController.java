package com.gabi.career_navigator_was.domain.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabi.career_navigator_was.domain.auth.dto.request.LoginReq;
import com.gabi.career_navigator_was.domain.auth.dto.request.PageAuthReq;
import com.gabi.career_navigator_was.domain.auth.dto.response.LoginRes;
import com.gabi.career_navigator_was.domain.auth.dto.response.ReissueRes;
import com.gabi.career_navigator_was.domain.auth.service.AuthService;
import com.gabi.career_navigator_was.global.dto.CommonResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	@PostMapping(value = "/login")
	public ResponseEntity<CommonResponse<LoginRes>> loginUser(@RequestBody @Valid LoginReq body) {
		return ResponseEntity.ok(authService.loginUser(body));
	}

	@PostMapping(value = "/logout")
	public ResponseEntity<CommonResponse<Void>> logoutUser() {
		return ResponseEntity.ok(authService.logoutUser());
	}

	@PostMapping(value = "/reissue")
	public ResponseEntity<CommonResponse<ReissueRes>> reissueToken() {
		return ResponseEntity.status(201).body(authService.reissueToken());
	}

	@PostMapping(value = "/page-auth")
	public ResponseEntity<CommonResponse<Void>> authenticatePage(@RequestBody @Valid PageAuthReq body) {
		return ResponseEntity.status(201).body(authService.authenticatePage(body));
	}
}
