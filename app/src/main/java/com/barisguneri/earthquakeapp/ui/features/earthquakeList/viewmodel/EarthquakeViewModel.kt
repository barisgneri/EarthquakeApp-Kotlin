package com.barisguneri.earthquakeapp.ui.features.earthquakeList.viewmodel

import com.barisguneri.earthquakeapp.core.domain.delegate.MVI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.barisguneri.earthquakeapp.ui.features.earthquakeList.viewmodel.EarthquakeListContract.UiState
import com.barisguneri.earthquakeapp.ui.features.earthquakeList.viewmodel.EarthquakeListContract.UiAction
import com.barisguneri.earthquakeapp.ui.features.earthquakeList.viewmodel.EarthquakeListContract.UiEffect

@HiltViewModel
class EarthquakeViewModel @Inject constructor() : MVI<UiState, UiEffect, UiAction>(UiState()) {

    override fun onAction(uiAction: UiAction) {
        super.onAction(uiAction)
        when (uiAction) {
            UiAction.GoSettings -> emitUiEffect(UiEffect.NavigateToSettings)
        }
    }
}