package com.barisguneri.earthquakeapp.ui.features.earthquakeList.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.barisguneri.earthquakeapp.core.domain.delegate.MVI
import com.barisguneri.earthquakeapp.domain.model.EarthquakeInfo
import com.barisguneri.earthquakeapp.domain.useCase.GetEarthquakesUseCase
import com.barisguneri.earthquakeapp.domain.useCase.GetMapEarthquakesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.barisguneri.earthquakeapp.ui.features.earthquakeList.viewmodel.EarthquakeListContract.UiState
import com.barisguneri.earthquakeapp.ui.features.earthquakeList.viewmodel.EarthquakeListContract.UiAction
import com.barisguneri.earthquakeapp.ui.features.earthquakeList.viewmodel.EarthquakeListContract.UiEffect
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


@HiltViewModel
class EarthquakeViewModel @Inject constructor(
    private val getEarthquakesUseCase: GetEarthquakesUseCase,
    private val getMapEarthquakesUseCase: GetMapEarthquakesUseCase
) : MVI<UiState, UiEffect, UiAction>(UiState()) {

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val pagingDataFlow: Flow<PagingData<EarthquakeInfo>> = uiState
        .map { it.filterState }
        .distinctUntilChanged()
        .debounce(2000)
        .map { currentFilters ->
            if (currentFilters.searchQuery.length < 3) {
                currentFilters.copy(searchQuery = "")
            } else {
                currentFilters
            }
        }
        .distinctUntilChanged()
        .flatMapLatest { currentFilters ->
            updateState { copy(isLoading = false) }
            getEarthquakesUseCase(currentFilters)
        }
        .cachedIn(viewModelScope)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val sourceMapEarthquakes: StateFlow<List<EarthquakeInfo>> = uiState
        .map { it.filterState }
        .distinctUntilChanged()
        .flatMapLatest { getMapEarthquakesUseCase(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val visibleMapPins: StateFlow<List<EarthquakeInfo>> = combine(
        sourceMapEarthquakes,
        uiState.map { it.mapBounds }.distinctUntilChanged()
    ) { earthquakes, bounds ->
        if (bounds == null) {
            earthquakes
        } else {
            earthquakes.filter { earthquake ->
                bounds.contains(earthquake.location.lat, earthquake.location.lng)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    override fun onAction(uiAction: UiAction) {
        super.onAction(uiAction)
        when (uiAction) {
            is UiAction.LoadEarthquakes -> {}
            is UiAction.Retry -> {}
            is UiAction.OnEarthquakeClick -> emitUiEffect(
                effect = UiEffect.NavigateToDetail(
                    uiAction.earthquakeId
                )
            )

            is UiAction.UpdateSearchQuery -> {
                updateState {
                    copy(
                        filterState = this.filterState.copy(searchQuery = uiAction.query),
                    )
                }
            }

            is UiAction.UpdateMagnitude -> {
                updateState {
                    copy(
                        filterState = this.filterState.copy(minMagnitude = uiAction.magnitude)
                    )
                }
            }

            is UiAction.UpdateTimeRange -> {
                updateState {
                    copy(
                        filterState = this.filterState.copy(timeRange = uiAction.range),
                        isLoading = true
                    )
                }
            }

            is UiAction.UpdateSortBy -> {
                updateState {
                    copy(
                        filterState = this.filterState.copy(sortBy = uiAction.option),
                        isLoading = true
                    )
                }
            }

            is UiAction.UpdateMapBounds -> updateState { copy(mapBounds = uiAction.bounds) }
        }
    }
}