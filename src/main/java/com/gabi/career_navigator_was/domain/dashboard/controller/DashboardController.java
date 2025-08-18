package com.gabi.career_navigator_was.domain.dashboard.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabi.career_navigator_was.domain.dashboard.dto.response.DashboardRes;
import com.gabi.career_navigator_was.domain.dashboard.service.DashboardService;
import com.gabi.career_navigator_was.domain.interview.dto.response.inner.QuestionRes;
import com.gabi.career_navigator_was.global.dto.CommonResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dashboard")
public class DashboardController {
	private final DashboardService dashboardService;

	@PostMapping(value = "/retrieve-dashboard")
	public ResponseEntity<CommonResponse<DashboardRes>> retrieveDashboard() {
		return ResponseEntity.ok(dashboardService.retrieveDashboard());
	}
}
