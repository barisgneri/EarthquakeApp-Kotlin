package com.barisguneri.earthquakeapp.core.domain.delegate

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MVIDelegate<UiState, UiEffect, UiAction> {
    val uiState: StateFlow<UiState>
    val uiEffect: Flow<UiEffect>

    val currentState: UiState
        get() = uiState.value

    fun updateState(block: UiState.() -> UiState)
    fun emitUiEffect(effect: UiEffect)

    fun onAction(uiAction: UiAction)
}