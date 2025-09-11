package com.barisguneri.earthquakeapp.presentation.earthquake

import androidx.paging.PagingData
import com.barisguneri.earthquakeapp.common.ErrorType
import com.barisguneri.earthquakeapp.domain.model.EarthquakeInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class EarthquakeScreenState(
    val pagingDataFlow: Flow<PagingData<EarthquakeInfo>> = emptyFlow(),
    val isLoading: Boolean = false,
    val error: ErrorType? = null
)

sealed interface EarthquakeScreenEvent {
    data object LoadEarthquakes : EarthquakeScreenEvent
    data object Retry : EarthquakeScreenEvent
}