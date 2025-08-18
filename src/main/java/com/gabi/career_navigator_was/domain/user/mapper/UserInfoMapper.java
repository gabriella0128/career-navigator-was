package com.gabi.career_navigator_was.domain.user.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.gabi.career_navigator_was.domain.user.dto.base.UserInfoDto;
import com.gabi.career_navigator_was.domain.user.entity.UserInfo;
import com.gabi.career_navigator_was.global.mapper.GenericMapper;

@Mapper(
	componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserInfoMapper extends GenericMapper<UserInfoDto, UserInfo> {
	@Override
	UserInfoDto toDto(UserInfo entity);

	@Override
	UserInfo toEntity(UserInfoDto dto);

	@Override
	List<UserInfoDto> getDtoList(List<UserInfo> entityList);

	@Override
	List<UserInfo> getEntityList(List<UserInfoDto> dtoList);
}
