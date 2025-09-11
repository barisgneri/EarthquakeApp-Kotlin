package com.barisguneri.earthquakeapp.ui.features.map.viewmodel

import com.barisguneri.earthquakeapp.core.data.ErrorType
import com.barisguneri.earthquakeapp.domain.model.EarthquakeInfo

object MapContract{
    data class UiState(
        val isLoading: Boolean = false,
        val earthquake: List<EarthquakeInfo>? = null,
        val error: ErrorType? = null
    )

    sealed interface UiAction{
        data object Retry : UiAction
        data object GetEarthquake : UiAction
        data class OnEarthquakeClick(val earthquakeId: String) : UiAction
        data object OnFilterClick : UiAction
    }

    sealed class UiEffect {
        data class ShowToast(val message: String) : UiEffect()
        data class NavigateToDetail(val earthquakeId: String) : UiEffect()
        data object NavigateToBack : UiEffect()
    }
}

