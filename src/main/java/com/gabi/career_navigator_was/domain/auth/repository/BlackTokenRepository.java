package com.gabi.career_navigator_was.domain.auth.repository;

import org.springframework.data.repository.CrudRepository;

import com.gabi.career_navigator_was.domain.auth.entity.BlackToken;

public interface BlackTokenRepository extends CrudRepository<BlackToken, String> {
}
