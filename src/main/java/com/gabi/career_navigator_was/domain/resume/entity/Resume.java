package com.gabi.career_navigator_was.domain.resume.entity;

import org.hibernate.annotations.SQLRestriction;

import com.gabi.career_navigator_was.global.code.YnType;
import com.gabi.career_navigator_was.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name="tb_resume")
@SQLRestriction("use_yn = 'Y' AND del_yn = 'N'")
@Getter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Resume extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "resume_idx")
	private Long resumeIdx;

	@Column(name = "user_idx", nullable = false)
	private Long userIdx;

	@Column(name = "title", length = 100, nullable = false)
	private String title;

	@Lob
	@Column(name = "summary", columnDefinition = "LONGTEXT")
	private String summary;

	@Column(name = "represent_yn")
	@Enumerated(EnumType.STRING)
	private YnType representYn;

}
