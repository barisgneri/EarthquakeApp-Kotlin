package com.barisguneri.earthquakeapp.ui.features.earthquakeList

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.barisguneri.earthquakeapp.core.common.ErrorType
import com.barisguneri.earthquakeapp.core.domain.delegate.MVI
import com.barisguneri.earthquakeapp.domain.useCase.GetEarthquakesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.barisguneri.earthquakeapp.ui.features.earthquakeList.EarthquakeListContract.UiState
import com.barisguneri.earthquakeapp.ui.features.earthquakeList.EarthquakeListContract.UiAction
import com.barisguneri.earthquakeapp.ui.features.earthquakeList.EarthquakeListContract.UiEffect


@HiltViewModel
class EarthquakeViewModel @Inject constructor(
    private val getEarthquakesUseCase: GetEarthquakesUseCase
) : MVI<UiState, UiEffect, UiAction>(UiState()) {



    init {
        onAction(UiAction.LoadEarthquakes)
    }

    override fun onAction(uiAction: UiAction) {
        super.onAction(uiAction)
        when (uiAction) {
            is UiAction.LoadEarthquakes -> loadEarthquakes()
            is UiAction.Retry -> loadEarthquakes()
            is UiAction.OnEarthquakeClick -> emitUiEffect(
                effect = UiEffect.NavigateToDetail(
                    uiAction.earthquakeId
                )
            )
        }
    }

    private fun loadEarthquakes() {
        viewModelScope.launch {
            updateState { copy(isLoading = true, error = null) }
            try {
                val earthquakesFlow = getEarthquakesUseCase()
                    .cachedIn(viewModelScope)

                updateState { copy(isLoading = false, pagingDataFlow = earthquakesFlow) }
            } catch (e: Exception) {
                updateState {
                    copy(
                        isLoading = false,
                        error = ErrorType.Unknown(apiCode = e.hashCode(), message = e.message)
                    )
                }
            }
        }
    }
}