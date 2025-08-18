package com.gabi.career_navigator_was.domain.interview.entity;

import java.time.LocalDateTime;

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
@Table(name="tb_daily_answer_eval")
@Getter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class DailyAnswerEval {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "eval_idx")
	private Long evalIdx;

	@Column(name = "answer_idx", nullable = false)
	private Long answerIdx;

	@Column(name = "user_idx", nullable = false)
	private Long userIdx;

	@Column(name = "score_overall", nullable = false)
	private Integer scoreOverall;

	@Lob
	@Column(name = "scores", columnDefinition = "LONGTEXT")
	private String scores;

	@Lob
	@Column(name = "feedback", columnDefinition = "LONGTEXT", nullable = false)
	private String feedback;

	@Lob
	@Column(name = "strength", columnDefinition = "LONGTEXT")
	private String strength;

	@Lob
	@Column(name = "improvements", columnDefinition = "LONGTEXT")
	private String improvements;

	@Column(name = "seed", length = 64, nullable = false)
	private String seed;

	@Column(name = "model_name", length = 100, nullable = false)
	private String modelName;

	@Column(name = "prompt_version", length = 50, nullable = false)
	private String promptVersion;

	@Column(name = "evaluated_at", nullable = false)
	private LocalDateTime evaluatedAt;
}
