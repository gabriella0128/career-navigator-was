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
@Table(name="tb_daily_answer")
@Getter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class DailyAnswer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "answer_idx")
	private Long answerIdx;

	@Column(name = "question_idx", nullable = false)
	private Long questionIdx;

	@Column(name = "user_idx", nullable = false)
	private Long userIdx;

	@Lob
	@Column(name = "answer", columnDefinition = "LONGTEXT", nullable = false)
	private String answer;

	@Column(name = "submitted_at", nullable = false)
	private LocalDateTime submittedAt;

}
