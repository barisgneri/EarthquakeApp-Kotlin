package com.barisguneri.earthquakeapp.domain.model

import androidx.annotation.StringRes
import com.barisguneri.earthquakeapp.R
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours


enum class TimeRange(
    @StringRes val titleResId: Int,
    val duration: Duration
) {
    LAST_1_HOUR(R.string.time_range_1_hour, 1.hours),
    LAST_12_HOURS(R.string.time_range_12_hours, 12.hours),
    LAST_24_HOURS(R.string.time_range_24_hours, 24.hours),
    LAST_7_DAYS(R.string.time_range_7_days, 7.days)
}