package com.barisguneri.earthquakeapp.ui.features.detail.viewmodel

import com.barisguneri.earthquakeapp.core.common.ErrorType
import com.barisguneri.earthquakeapp.domain.model.EarthquakeDetail

object EarthquakeDetailContract {
    data class UiState(
        val isLoading: Boolean = false,
        val earthquake: EarthquakeDetail? = null,
        val error: ErrorType? = null
    )

    sealed class UiEffect {
        data class ShowToast(val message: String) : UiEffect()
        data object NavigateBack : UiEffect()
    }

    sealed interface UiAction{
        data object Retry : UiAction
        data class Load(val earthquakeId: String) : UiAction
        data object NavigateBack : UiAction
    }

}