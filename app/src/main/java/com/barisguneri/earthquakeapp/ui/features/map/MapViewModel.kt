package com.barisguneri.earthquakeapp.ui.features.map

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.barisguneri.earthquakeapp.core.common.Resource
import com.barisguneri.earthquakeapp.core.domain.delegate.MVI
import com.barisguneri.earthquakeapp.domain.useCase.GetAllEarthquakesUseCase
import com.barisguneri.earthquakeapp.domain.useCase.GetEarthquakesUseCase
import com.barisguneri.earthquakeapp.ui.features.map.MapContract.UiAction
import com.barisguneri.earthquakeapp.ui.features.map.MapContract.UiState
import com.barisguneri.earthquakeapp.ui.features.map.MapContract.UiEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(private val getAllEarthquakesUseCase: GetAllEarthquakesUseCase) :
    MVI<UiState, UiEffect, UiAction>(UiState()) {

    init {
        onAction(UiAction.GetEarthquake)
    }

    override fun onAction(uiAction: UiAction) {
        when (uiAction) {
            is UiAction.GetEarthquake -> getAllEarthquakes()
            is UiAction.Retry -> getAllEarthquakes()
            is UiAction.OnEarthquakeClick -> {
                emitUiEffect(
                    effect = UiEffect.NavigateToDetail(
                        uiAction.earthquakeId
                    )
                )
            }
        }
    }

    private fun getAllEarthquakes() {
        getAllEarthquakesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    updateState {
                        copy(earthquake = result.data, isLoading = false)
                    }
                }

                is Resource.Error -> {
                    updateState {
                        copy(error = result.errorType, isLoading = false)
                    }
                }

                is Resource.Loading -> {
                    updateState {
                        copy(isLoading = true)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}

