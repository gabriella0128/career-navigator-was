package com.gabi.career_navigator_was.domain.resume.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLRestriction;

import com.gabi.career_navigator_was.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name="tb_experience")
@SQLRestriction("use_yn = 'Y' AND del_yn = 'N'")
@Getter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Experience extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "experience_idx")
	private Long experienceIdx;

	@Column(name = "resume_idx", nullable = false)
	private Long resumeIdx;

	@Column(name = "company_name", length = 100, nullable = false)
	private String companyName;

	@Column(name = "position", length = 100)
	private String position;

	@Column(name = "start_date")
	private LocalDateTime startDate;

	@Column(name = "end_date")
	private LocalDateTime endDate;

	@Lob
	@Column(name = "description", columnDefinition = "LONGTEXT")
	private String description;

}
