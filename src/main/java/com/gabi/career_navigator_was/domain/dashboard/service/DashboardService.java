package com.gabi.career_navigator_was.domain.dashboard.service;

import com.gabi.career_navigator_was.domain.dashboard.dto.response.DashboardRes;
import com.gabi.career_navigator_was.global.dto.CommonResponse;

public interface DashboardService {
	CommonResponse<DashboardRes> retrieveDashboard();
}
