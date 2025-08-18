package com.gabi.career_navigator_was.domain.learning.entity;

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
@Table(name="tb_learning_goal")
@Getter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class LearningGoal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "goal_idx")
	private Long goalIdx;

	@Column(name="plan_idx", nullable = false)
	private Long planIdx;

	@Column(name = "title", length = 200, nullable = false)
	private String title;

	@Column(name = "metric", length = 200, nullable = false)
	private String metric;

	@Column(name = "target_value", length = 200, nullable = false)
	private String targetValue;

	@Column(name = "priority", nullable = false)
	private Integer priority;

	@Column(name = "category", length = 50, nullable = false)
	private String category;
}
