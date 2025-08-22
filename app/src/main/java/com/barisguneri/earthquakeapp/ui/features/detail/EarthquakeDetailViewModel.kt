package com.barisguneri.earthquakeapp.ui.features.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.barisguneri.earthquakeapp.core.common.Resource
import com.barisguneri.earthquakeapp.core.domain.delegate.MVI
import com.barisguneri.earthquakeapp.domain.useCase.GetEarthquakeDetailUseCase
import com.barisguneri.earthquakeapp.ui.navigation.MainScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import com.barisguneri.earthquakeapp.ui.features.detail.EarthquakeDetailContract.UiState
import com.barisguneri.earthquakeapp.ui.features.detail.EarthquakeDetailContract.UiEffect
import com.barisguneri.earthquakeapp.ui.features.detail.EarthquakeDetailContract.UiAction

@HiltViewModel
class EarthquakeDetailViewModel @Inject constructor(
    private val getEarthquakeDetailUseCase: GetEarthquakeDetailUseCase,
    savedStateHandle: SavedStateHandle
) : MVI<UiState, UiEffect, UiAction>(UiState()) {

    private val args = savedStateHandle.toRoute<MainScreen.Detail>()

    init {
        loadDetail(args.itemId)
    }

    override fun onAction(uiAction: UiAction) {
        when (uiAction) {
            is UiAction.Retry -> {
                loadDetail(args.itemId)
            }

            is UiAction.Load -> {
                loadDetail(uiAction.earthquakeId)
            }
        }
    }

    private fun loadDetail(id: String) {
        getEarthquakeDetailUseCase(id).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    updateState {
                        copy(isLoading = true)
                    }
                }

                is Resource.Success -> {
                    updateState {
                        copy(isLoading = false, earthquake = resource.data)
                    }
                }

                is Resource.Error -> {
                    updateState {
                        copy(isLoading = true, error = resource.errorType)
                    }
                    emitUiEffect(UiEffect.ShowToast("Hata Oluştu"))
                }
            }
        }.launchIn(viewModelScope)
    }
}