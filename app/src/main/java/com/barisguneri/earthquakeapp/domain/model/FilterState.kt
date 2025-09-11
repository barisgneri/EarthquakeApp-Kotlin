package com.barisguneri.earthquakeapp.domain.model

data class FilterState(
    val searchQuery: String = "",
    val minMagnitude: Float = 10.0f,
    val timeRange: TimeRange = TimeRange.LAST_7_DAYS,
    val sortBy: SortOption = SortOption.DATE
)