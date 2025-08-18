package com.gabi.career_navigator_was.global.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CarnavCustomErrorCode {
	INVALID_PARAMETER(400, "CARNAV-E1001", "잘못된 입력 값 입니다."),
	// User/Account Related - 3000
	NOT_FOUND_USER(404, "CARNAV-E3001", "사용자를 찾을 수 없습니다."),
	CONFLICT_USER_ID(409, "CARNAV-E3002", "중복된 사용자 ID입니다."),
	CONFLICT_USER_EMAIL(409, "CARNAV-E3003", "중복된 이메일입니다."),
	INVALID_PATTERN_PESSWORD(400, "CARNAV-E3004", "잘못된 비밀번호 형식입니다."),
	INVALID_PATTERN_EMAIL(400, "CARNAV-E3005", "잘못된 이메일 형식입니다."),
	EXCEEDED_LOGIN_ACCESS(429, "CARNAV-E3006", "최대 로그인 시도 횟수를 초과했습니다."),
	INVALID_PASSWORD(400, "CARNAV-E3007", "잘못된 비밀번호입니다."),
	ALREADY_USED_PASSWORD(400, "CARNAV-E3008", "이전에 사용한 비밀번호입니다."),
	WITHDRAW_USER(404, "CARNAV-E3009", "탈퇴한 회원입니다."),
	// Database/Resource - 4000
	NOT_FOUND_RESOURCE(404, "CARNAV-E4001", "리소스를 찾을 수 없습니다."),
	CONFLICT_FOUND_RESOURCE(409, "CARNAV-E4002", "중복된 리소스입니다."),
	CONNECTION_FAILED(500, "CARNAV-E4003", "데이터베이스 연결 오류입니다."),
	EXCUTE_FAILED_QUERY(500, "CARNAV-E4004", "데이터베이스 쿼리 실행 오류입니다."),
	OCCURED_DEAD_LOCK(409, "CARNAV-E4005", "데드락이 발생했습니다."),
	VIOLATION_DATA_INTEGRITY(409, "CARNAV-E4006", "데이터 무결성 위반입니다."),
	DATA_SAVE_ERROR(409, "CARNAV-E4007", "데이터 저장 중 오류가 발생했습니다."),
	DATA_DELETE_ERROR(409, "CARNAV-E4007", "데이터 삭제 중 오류가 발생했습니다."),
	// File Operations - 6000
	NOT_FOUND_FILE(404, "CARNAV-E6001", "파일을 찾을 수 없습니다."),
	FAILED_FILE_UPLOAD(500, "CARNAV-E6002", "파일 업로드에 실패했습니다."),
	FAILED_FILE_DOWNLOAD(500, "CARNAV-E6003", "파일 다운로드에 실패했습니다."),
	INVALLID_FILE_TYPE(400, "CARNAV-E6004", "잘못된 파일 형식입니다."),
	EXCEEDED_FILE_SIZE(413, "CARNAV-E6005", "파일 크기가 제한을 초과했습니다."),
	FILE_STORAGE_ERROR(500, "CARNAV-E6006", "파일 저장소 오류가 발생했습니다."),
	// Authentication & Authorization - 2000
	UNAUTHORIZED_ACCESS(403, "CARNAV-E2001", "인증되지 않은 접근입니다."),
	EXPIRED_TOKEN(401, "CARNAV-E2002", "만료된 토큰입니다."),
	INVALID_TOKEN(403, "CARNAV-E2003", "유효 하지 않은 토큰 입니다."),
	AUTHORITY_NECESSARY(403, "CARNAV-E2004", "권한이 부족합니다."),
	UNAUTHORIZED_AUTH_INFO(403, "CARNAV-E2005", "잘못된 인증 정보 입니다."),
	LOCKED_ACCOUNT(403, "CARNAV-E2006", "계정이 잠겼습니다."),
	DEACTIVATED_ACCOUNT(403, "CARNAV-E2007", "비활성화된 계정 입니다."),
	EMAIL_VERIFY_CODE_INCORRECT(403, "CARNAV-E2008", "이메일 인증 코드가 일치하지 않습니다."),
	EXCEEDED_EMAIL_AUTH(403, "CARNAV-E2009", "이메일 인증 오류 횟수를 초과하였습니다."),
	NO_AUTHORIZATION_FOUND(403, "CARNAV-E2010", "해당하는 권한이 없습니다."),

	// Custom - 9000
	NOT_YOUR_RESUME(403, "CARNAV-E9001", "해당 이력서의 주인이 아닙니다."),
	REPRESENT_RESUME_NOT_FOUND(403, "CARNAV-E9002", "등록된 대표이력서가 없습니다."),
	DAILY_QUESTION_ALREADY_EXIST(403, "CARNAV-E9003", "이미 오늘자 문제가 생성 되었습니다."),
	JSON_PARSING(500, "CARNAV-E9004", "JSON Parsing에 실패했습니다."),
	LLM_FEIGN_ERROR(500, "CARNAV-E9005", "Feign Error 가 발생했습니다.");
	private final int status;
	private final String errorCode;
	private final String message;
}
