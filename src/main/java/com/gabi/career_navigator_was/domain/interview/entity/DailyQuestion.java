package com.gabi.career_navigator_was.domain.interview.entity;

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
@Table(name="tb_daily_question")
@Getter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class DailyQuestion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "question_idx")
	private Long questionIdx;

	@Column(name = "batch_idx", nullable = false)
	private Long batchIdx;

	@Column(name = "category", length=30, nullable = false)
	private String category;

	@Column(name = "difficulty", nullable = false)
	private Integer difficulty;

	@Lob
	@Column(name = "question", columnDefinition = "LONGTEXT", nullable = false)
	private String question;

	@Lob
	@Column(name = "expected_points", columnDefinition = "LONGTEXT", nullable = false)
	private String expectedPoints;

	@Lob
	@Column(name = "evidence", columnDefinition = "LONGTEXT", nullable = false)
	private String evidence;

	@Column(name = "q_hash", length = 40, nullable = false)
	private String qHash;

	@Column(name = "position", nullable = false)
	private Integer position;

}
