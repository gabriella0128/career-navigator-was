package com.gabi.career_navigator_was.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabi.career_navigator_was.domain.user.dto.request.IdCheckReq;
import com.gabi.career_navigator_was.domain.user.dto.request.InsertUserReq;
import com.gabi.career_navigator_was.domain.user.dto.response.IdCheckRes;
import com.gabi.career_navigator_was.domain.user.dto.response.UserInfoRes;
import com.gabi.career_navigator_was.domain.user.service.UserService;
import com.gabi.career_navigator_was.global.dto.CommonResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

	private final UserService userService;

	@PostMapping(value = "/signup/check-id")
	public ResponseEntity<CommonResponse<IdCheckRes>> checkIdDuplication(@RequestBody @Valid IdCheckReq body) {
		return ResponseEntity.ok(userService.checkIdDuplication(body));
	}

	@PostMapping(value = "/signup/insert-user")
	public ResponseEntity<CommonResponse<Void>> registerUser(@RequestBody @Valid InsertUserReq body) {
		return ResponseEntity.ok(userService.insertUser(body));
	}

	@PostMapping(value = "/detail/retrieve-user")
	public ResponseEntity<CommonResponse<UserInfoRes>> retrieveUser() {
		return ResponseEntity.ok(userService.retrieveUser());
	}
}
