package com.gabi.career_navigator_was.domain.auth.service;

import com.gabi.career_navigator_was.domain.auth.dto.request.LoginReq;
import com.gabi.career_navigator_was.domain.auth.dto.request.PageAuthReq;
import com.gabi.career_navigator_was.domain.auth.dto.response.LoginRes;
import com.gabi.career_navigator_was.domain.auth.dto.response.ReissueRes;
import com.gabi.career_navigator_was.global.dto.CommonResponse;

public interface AuthService {
	CommonResponse<LoginRes> loginUser(LoginReq body);
	CommonResponse<Void> logoutUser();
	CommonResponse<ReissueRes> reissueToken();
	CommonResponse<Void> authenticatePage(PageAuthReq body);

}
