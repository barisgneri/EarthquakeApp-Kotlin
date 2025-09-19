package com.barisguneri.earthquakeapp.ui.main

import com.barisguneri.earthquakeapp.core.data.ErrorType
import com.barisguneri.earthquakeapp.domain.model.FilterState
import com.barisguneri.earthquakeapp.domain.model.SortOption
import com.barisguneri.earthquakeapp.domain.model.TimeRange
import org.osmdroid.util.BoundingBox

object SharedContract {
    data class UiState(
        val filterState: FilterState = FilterState(),
        val isLoading: Boolean = false,
        val error: ErrorType? = null,
        val mapBounds: BoundingBox? = null
    )
    sealed interface UiAction{
        data object Retry : UiAction
        data object LoadEarthquakes : UiAction
        data class UpdateSearchQuery(val query: String) : UiAction
        data class UpdateMagnitude(val magnitude: Float) : UiAction
        data class UpdateTimeRange(val range: TimeRange) : UiAction
        data class UpdateSortBy(val sortBy: SortOption) : UiAction
        data class UpdateMapBounds(val bounds: BoundingBox) : UiAction
        data class OnEarthquakeClick(val earthquakeId: String) : UiAction
        data object OnFilterClick : UiAction

    }
    sealed class UiEffect {
        data class ShowToast(val message: String) : UiEffect()
        data class NavigateToDetail(val earthquakeId: String) : UiEffect()
    }
}