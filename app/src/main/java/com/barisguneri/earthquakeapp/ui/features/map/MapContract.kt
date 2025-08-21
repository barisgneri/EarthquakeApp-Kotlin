package com.barisguneri.earthquakeapp.ui.features.map

import com.barisguneri.earthquakeapp.core.common.ErrorType
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
    }

    sealed class UiEffect {
        data class ShowToast(val message: String) : UiEffect()
    }
}

