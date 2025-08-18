package com.gabi.career_navigator_was.domain.dashboard.repository.querydsl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gabi.career_navigator_was.domain.dashboard.dto.response.inner.CategoryStat;
import com.gabi.career_navigator_was.domain.dashboard.dto.response.inner.ScoreBin;
import com.gabi.career_navigator_was.domain.interview.dto.base.DailyAnswerEvalDto;

public interface DashboardRepositoryCustom {
	Map<String, Integer> findKpis(Long userIdx, LocalDateTime dayStart, LocalDateTime dayEnd, LocalDateTime weekStart, LocalDateTime weekEnd, LocalDateTime monthStart, LocalDateTime monthEnd);

	List<ScoreBin> findScoreHistogram(Long userIdx, LocalDateTime start, LocalDateTime end);

	List<CategoryStat> findCategoryStats(Long userIdx, LocalDateTime start, LocalDateTime end);

	Map<LocalDate, Integer> countPerDay(Long userIdx, LocalDateTime start, LocalDateTime end);

	Set<LocalDate> distinctSubmitDates(Long userIdx, LocalDateTime start, LocalDateTime end);

	List<DailyAnswerEvalDto> findEvalsForHighlights(Long userIdx, LocalDateTime start, LocalDateTime end);
}
