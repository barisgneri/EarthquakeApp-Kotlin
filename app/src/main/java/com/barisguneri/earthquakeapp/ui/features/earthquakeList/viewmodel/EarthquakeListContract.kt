package com.barisguneri.earthquakeapp.ui.features.earthquakeList.viewmodel

import com.barisguneri.earthquakeapp.core.data.ErrorType

object EarthquakeListContract{
    data class UiState(
        val isLoading: Boolean = false,
        val error: ErrorType? = null,
    )
    sealed interface UiAction{
        data object GoSettings : UiAction
    }
    sealed class UiEffect {
        data class ShowToast(val message: String) : UiEffect()
        data object NavigateToSettings : UiEffect()
    }
}