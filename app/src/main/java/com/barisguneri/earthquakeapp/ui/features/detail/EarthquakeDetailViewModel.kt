package com.barisguneri.earthquakeapp.ui.features.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.savedstate.savedState
import com.barisguneri.earthquakeapp.core.common.Resource
import com.barisguneri.earthquakeapp.domain.useCase.GetEarthquakeDetailUseCase
import com.barisguneri.earthquakeapp.ui.navigation.MainScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class EarthquakeDetailViewModel @Inject constructor(
    private val getEarthquakeDetailUseCase: GetEarthquakeDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(EarthquakeDetailScreenState())
    val uiState = _uiState.asStateFlow()

    private val args = savedStateHandle.toRoute<MainScreen.Detail>()
    private var currentEarthquakeId: String = args.itemId

    init {
        onEvent(EarthquakeDetailScreenEvent.Load(currentEarthquakeId))
    }


    fun onEvent(event: EarthquakeDetailScreenEvent) {
        when (event) {
            is EarthquakeDetailScreenEvent.Load -> {
                this.currentEarthquakeId = event.earthquakeId
                loadDetail(event.earthquakeId)
            }
            is EarthquakeDetailScreenEvent.Retry -> {
                currentEarthquakeId?.let { id ->
                    loadDetail(id)
                }
            }
        }
    }

    private fun loadDetail(id: String) {
        getEarthquakeDetailUseCase(id).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _uiState.value = EarthquakeDetailScreenState(isLoading = true)
                }
                is Resource.Success -> {
                    _uiState.value = EarthquakeDetailScreenState(earthquake = resource.data)
                }
                is Resource.Error -> {
                    _uiState.value = EarthquakeDetailScreenState(error = resource.errorType)
                }
            }
        }.launchIn(viewModelScope)
    }
}