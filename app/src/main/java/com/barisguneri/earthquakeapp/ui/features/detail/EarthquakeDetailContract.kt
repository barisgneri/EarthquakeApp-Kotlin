package com.barisguneri.earthquakeapp.ui.features.detail

import androidx.paging.PagingData
import com.barisguneri.earthquakeapp.core.common.ErrorType
import com.barisguneri.earthquakeapp.domain.model.EarthquakeDetail

data class EarthquakeDetailScreenState(
    val isLoading: Boolean = false,
    val earthquake: EarthquakeDetail? = null,
    val error: ErrorType? = null
)

sealed interface EarthquakeDetailScreenEvent {
    data object Retry : EarthquakeDetailScreenEvent
    data class Load(val earthquakeId: String) : EarthquakeDetailScreenEvent
}