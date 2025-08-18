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
@Table(name="tb_learning_task")
@Getter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class LearningTask {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "task_idx")
	private Long taskIdx;

	@Column(name = "goal_idx", nullable = false)
	private Long goalIdx;

	@Column(name = "task_title", length = 200, nullable = false)
	private String taskTitle;

	@Column(name = "resource_url", length = 500)
	private String resourceUrl;

	@Column(name = "week_no", nullable = false)
	private Integer weekNo;

}
