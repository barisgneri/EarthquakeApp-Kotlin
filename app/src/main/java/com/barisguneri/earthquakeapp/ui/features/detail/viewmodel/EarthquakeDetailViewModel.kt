package com.barisguneri.earthquakeapp.ui.features.detail.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.barisguneri.earthquakeapp.core.data.Resource
import com.barisguneri.earthquakeapp.core.domain.delegate.MVI
import com.barisguneri.earthquakeapp.domain.useCase.GetEarthquakeDetailUseCase
import com.barisguneri.earthquakeapp.ui.navigation.MainScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import com.barisguneri.earthquakeapp.ui.features.detail.viewmodel.EarthquakeDetailContract.UiState
import com.barisguneri.earthquakeapp.ui.features.detail.viewmodel.EarthquakeDetailContract.UiEffect
import com.barisguneri.earthquakeapp.ui.features.detail.viewmodel.EarthquakeDetailContract.UiAction

@HiltViewModel
class EarthquakeDetailViewModel @Inject constructor(
    private val getEarthquakeDetailUseCase: GetEarthquakeDetailUseCase,
    savedStateHandle: SavedStateHandle
) : MVI<UiState, UiEffect, UiAction>(UiState()) {

    private val args = savedStateHandle.toRoute<MainScreen.Detail>()

    init {
        onAction(UiAction.Load(args.itemId))
    }

    override fun onAction(uiAction: UiAction) {
        when (uiAction) {
            is UiAction.Retry -> {
                loadDetail(args.itemId)
            }

            is UiAction.Load -> {
                loadDetail(uiAction.earthquakeId)
            }

            is UiAction.NavigateBack -> emitUiEffect(UiEffect.NavigateBack)
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