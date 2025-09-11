package com.barisguneri.earthquakeapp.ui.features.map.viewmodel

import com.barisguneri.earthquakeapp.core.domain.delegate.MVI
import com.barisguneri.earthquakeapp.ui.features.map.viewmodel.MapContract.UiAction
import com.barisguneri.earthquakeapp.ui.features.map.viewmodel.MapContract.UiState
import com.barisguneri.earthquakeapp.ui.features.map.viewmodel.MapContract.UiEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor() :
    MVI<UiState, UiEffect, UiAction>(UiState()) {

    override fun onAction(uiAction: UiAction) {
        when (uiAction) {
            is UiAction.GetEarthquake -> {}
            is UiAction.Retry -> {}
            is UiAction.OnEarthquakeClick -> {
                emitUiEffect(
                    effect = UiEffect.NavigateToDetail(
                        uiAction.earthquakeId
                    )
                )
            }

            is UiAction.OnFilterClick -> emitUiEffect(effect = UiEffect.NavigateToBack)
        }
    }
}

