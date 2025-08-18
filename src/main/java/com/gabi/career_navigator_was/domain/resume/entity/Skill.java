package com.gabi.career_navigator_was.domain.resume.entity;

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
@Table(name="tb_skill")
@SQLRestriction("use_yn = 'Y' AND del_yn = 'N'")
@Getter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Skill extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "skill_idx")
	private Long skillIdx;

	@Column(name = "resume_idx", nullable = false)
	private Long resumeIdx;

	@Column(name = "skill_name", length = 100, nullable = false)
	private String skillName;

	@Column(name = "proficiency", length = 100)
	private String proficiency;
}
