package com.barisguneri.earthquakeapp.ui.features.map.viewmodel

import com.barisguneri.earthquakeapp.core.data.ErrorType

object MapContract{
    data class UiState(
        val isLoading: Boolean = false,
        val error: ErrorType? = null
    )

    sealed interface UiAction{
        data object Retry : UiAction
    }

    sealed class UiEffect {
        data class ShowToast(val message: String) : UiEffect()
    }
}

