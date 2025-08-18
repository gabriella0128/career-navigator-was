package com.gabi.career_navigator_was.domain.auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gabi.career_navigator_was.domain.auth.entity.Token;

@Repository
public interface TokenRepository extends CrudRepository<Token, String> {
}
