package com.barisguneri.earthquakeapp.domain.model

import androidx.compose.ui.res.stringResource

enum class TimeRange(val title: String) {
    LAST_1_HOURS("Last 24 Hours"),
    LAST_12_HOURS("Last 12 Hours"),
    LAST_24_HOURS("Last 24 Hours"),
    LAST_7_DAYS("Last 7 Days"),
}