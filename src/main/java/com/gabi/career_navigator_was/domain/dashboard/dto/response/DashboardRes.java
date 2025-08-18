package com.gabi.career_navigator_was.domain.dashboard.dto.response;

import java.util.List;

import com.gabi.career_navigator_was.domain.dashboard.dto.response.inner.CalendarBlock;
import com.gabi.career_navigator_was.domain.dashboard.dto.response.inner.CategoryStat;
import com.gabi.career_navigator_was.domain.dashboard.dto.response.inner.Highlights;
import com.gabi.career_navigator_was.domain.dashboard.dto.response.inner.KpiItem;
import com.gabi.career_navigator_was.domain.dashboard.dto.response.inner.ScoreBin;

import lombok.Builder;

@Builder(toBuilder = true)
public record DashboardRes(
	List<KpiItem> kpis,
	List<ScoreBin> scoreHistogram,
	List<CategoryStat> categoryStats,
	CalendarBlock calendar,
	Highlights highlights
) {
}
