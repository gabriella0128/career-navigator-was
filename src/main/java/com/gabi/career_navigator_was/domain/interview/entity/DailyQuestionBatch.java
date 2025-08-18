package com.gabi.career_navigator_was.domain.interview.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.SQLRestriction;

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
@Table(name="tb_daily_question_batch")
@Getter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class DailyQuestionBatch {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "batch_idx")
	private Long batchIdx;

	@Column(name = "session_uid", length = 100, nullable = false)
	private String sessionUid;

	@Column(name="user_idx", nullable = false)
	private Long userIdx;

	@Column(name = "practice_date", nullable = false)
	private LocalDate practiceDate;

	@Column(name = "resume_idx", nullable = false)
	private Long resumeIdx;

	@Lob
	@Column(name = "resume_snapshot", columnDefinition = "LONGTEXT", nullable = false)
	private String resumeSnapshot;

	@Column(name = "seed", length = 64, nullable = false)
	private String seed;

	@Column(name = "model_name", length = 100, nullable = false)
	private String modelName;

	@Column(name = "prompt_version", length = 50, nullable = false)
	private String promptVersion;

	@Column(name = "generated_at", nullable = false)
	private LocalDateTime generatedAt;

}
