package com.gabi.career_navigator_was.domain.user.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLRestriction;

import com.gabi.career_navigator_was.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name="tb_user_info")
@SQLRestriction("use_yn = 'Y' AND del_yn = 'N'")
@Getter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_idx")
	private Long userIdx;

	@Column(name = "user_id", length = 20)
	private String userId;

	@Column(name = "user_passwd")
	private String userPasswd;

	@Column(name = "user_name", length = 10)
	private String userName;

	@Column(name = "user_email", length = 50)
	private String userEmail;

	@Column(name = "last_login_dt")
	private LocalDateTime lastLoginDt;
}
