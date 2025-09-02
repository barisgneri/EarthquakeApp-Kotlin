package com.barisguneri.earthquakeapp.ui.features.earthquakeList

import androidx.paging.PagingData
import com.barisguneri.earthquakeapp.core.common.ErrorType
import com.barisguneri.earthquakeapp.domain.model.EarthquakeInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

object EarthquakeListContract{
    data class UiState(
        val pagingDataFlow: Flow<PagingData<EarthquakeInfo>> = emptyFlow(),
        val isLoading: Boolean = false,
        val error: ErrorType? = null
    )
    sealed interface UiAction{
        data object Retry : UiAction
        data object LoadEarthquakes : UiAction
        data class OnEarthquakeClick(val earthquakeId: String) : UiAction
    }
    sealed class UiEffect {
        data class ShowToast(val message: String) : UiEffect()
        data class NavigateToDetail(val earthquakeId: String) : UiEffect()
    }
}