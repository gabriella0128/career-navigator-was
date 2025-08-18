package com.gabi.career_navigator_was.domain.resume.entity;

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
@Table(name="tb_certificate")
@SQLRestriction("use_yn = 'Y' AND del_yn = 'N'")
@Getter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Certificate extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "certificate_idx")
	private Long certificateIdx;

	@Column(name = "resume_idx", nullable = false)
	private Long resumeIdx;

	@Column(name = "certificate_name", length = 100, nullable = false)
	private String certificateName;

	@Column(name = "issued_by", length = 100)
	private String issuedBy;

	@Column(name = "issue_date")
	private LocalDateTime issueDate;
}
