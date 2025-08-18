package com.gabi.career_navigator_was.global.entity;

import static com.gabi.career_navigator_was.global.code.YnType.*;

import java.time.LocalDateTime;
import java.util.Objects;

import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.gabi.career_navigator_was.global.code.YnType;
import com.gabi.career_navigator_was.global.config.auditor.AuditorProvider;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@MappedSuperclass
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@SQLRestriction("use_yn = 'Y' AND del_yn = 'N'")
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
	@Column(name = "use_yn")
	@Enumerated(EnumType.STRING)
	private YnType useYn;

	@Column(name = "del_yn")
	@Enumerated(EnumType.STRING)
	private YnType delYn;

	@Column(name = "create_id", nullable = false, updatable = false, length = 20)
	private String createId;

	@Column(name = "create_dt", nullable = false, updatable = false)
	@CreatedDate
	private LocalDateTime createDt;

	@Column(name = "modify_id", length = 20)
	private String modifyId;

	@Column(name = "modify_dt")
	@LastModifiedDate
	private LocalDateTime modifyDt;

	@PrePersist
	public void prePersist() {
		this.useYn = Y;
		this.delYn = N;

		if (Objects.isNull(this.createDt)) {
			this.createDt = LocalDateTime.now();
		}
		if (Objects.isNull(this.modifyDt)) {
			this.modifyDt = LocalDateTime.now();
		}

		this.createId = AuditorProvider.getAuditorAware().getAuditor(this.createId).orElse("anonymous");
		this.modifyId = AuditorProvider.getAuditorAware().getAuditor(this.modifyId).orElse("anonymous");
	}

	@PreUpdate
	public void preUpdate() {
		this.modifyId = AuditorProvider.getAuditorAware().getAuditor(this.modifyId).orElse("anonymous");
	}

}