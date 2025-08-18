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
@Table(name="tb_education")
@SQLRestriction("use_yn = 'Y' AND del_yn = 'N'")
@Getter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Education extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "education_idx")
	private Long educationIdx;

	@Column(name = "resume_idx", nullable = false)
	private Long resumeIdx;

	@Column(name = "school_name", length = 100, nullable = false)
	private String schoolName;

	@Column(name = "degree", length = 100)
	private String degree;

	@Column(name = "major", length = 100)
	private String major;

	@Column(name = "start_date")
	private LocalDateTime startDate;

	@Column(name = "end_date")
	private LocalDateTime endDate;

	@Lob
	@Column(name = "description", columnDefinition = "LONGTEXT")
	private String description;
}
