package com.barisguneri.earthquakeapp.ui.features.earthquakeList

import androidx.paging.PagingData
import com.barisguneri.earthquakeapp.core.common.ErrorType
import com.barisguneri.earthquakeapp.domain.model.EarthquakeInfo
import com.barisguneri.earthquakeapp.domain.model.FilterState
import com.barisguneri.earthquakeapp.domain.model.SortOption
import com.barisguneri.earthquakeapp.domain.model.TimeRange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

object EarthquakeListContract{
    data class UiState(
        val filterState: FilterState = FilterState(),
        val isLoading: Boolean = false,
        val error: ErrorType? = null
    )
    sealed interface UiAction{
        data object Retry : UiAction
        data object LoadEarthquakes : UiAction
        data class OnEarthquakeClick(val earthquakeId: String) : UiAction
        data class UpdateSearchQuery(val query: String) : UiAction
        data class UpdateMagnitude(val magnitude: Float) : UiAction
        data class UpdateTimeRange(val range: TimeRange) : UiAction
        data class UpdateSortBy(val option: SortOption) : UiAction
    }
    sealed class UiEffect {
        data class ShowToast(val message: String) : UiEffect()
        data class NavigateToDetail(val earthquakeId: String) : UiEffect()
    }
}