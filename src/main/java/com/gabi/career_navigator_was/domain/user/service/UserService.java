package com.gabi.career_navigator_was.domain.user.service;

import com.gabi.career_navigator_was.domain.user.dto.request.IdCheckReq;
import com.gabi.career_navigator_was.domain.user.dto.request.InsertUserReq;
import com.gabi.career_navigator_was.domain.user.dto.response.IdCheckRes;
import com.gabi.career_navigator_was.domain.user.dto.response.UserInfoRes;
import com.gabi.career_navigator_was.global.dto.CommonResponse;

public interface UserService {
	CommonResponse<IdCheckRes> checkIdDuplication(IdCheckReq body);

	CommonResponse<Void> insertUser(InsertUserReq body);

	CommonResponse<UserInfoRes> retrieveUser();
}
