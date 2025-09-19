package com.barisguneri.earthquakeapp.ui.main

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.barisguneri.earthquakeapp.core.domain.delegate.MVI
import com.barisguneri.earthquakeapp.domain.model.EarthquakeInfo
import com.barisguneri.earthquakeapp.domain.useCase.GetEarthquakesUseCase
import com.barisguneri.earthquakeapp.domain.useCase.GetMapEarthquakesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val getEarthquakesUseCase: GetEarthquakesUseCase,
    private val getMapEarthquakesUseCase: GetMapEarthquakesUseCase
) :
    MVI<SharedContract.UiState, SharedContract.UiEffect, SharedContract.UiAction>(
        SharedContract.UiState()
    ) {

    override fun onAction(uiAction: SharedContract.UiAction) {
        super.onAction(uiAction)
        when (uiAction) {
            SharedContract.UiAction.LoadEarthquakes -> {
            }
            SharedContract.UiAction.Retry -> {
            }
            is SharedContract.UiAction.UpdateMagnitude -> {
                updateState { copy(filterState = filterState.copy(minMagnitude = uiAction.magnitude)) }
            }
            is SharedContract.UiAction.UpdateMapBounds -> {
                updateState { copy(mapBounds = uiAction.bounds) }
            }
            is SharedContract.UiAction.UpdateSearchQuery -> {
                updateState { copy(filterState = filterState.copy(searchQuery = uiAction.query)) }
            }
            is SharedContract.UiAction.UpdateSortBy -> {
                updateState { copy(filterState = filterState.copy(sortBy = uiAction.sortBy)) }
            }
            is SharedContract.UiAction.UpdateTimeRange -> {
                updateState { copy(filterState = filterState.copy(timeRange = uiAction.range)) }
            }
            is SharedContract.UiAction.OnEarthquakeClick -> {
                emitUiEffect(SharedContract.UiEffect.NavigateToDetail(uiAction.earthquakeId))
            }
            SharedContract.UiAction.OnFilterClick -> {
                emitUiEffect(SharedContract.UiEffect.ShowToast("Filter Clicked"))
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val pagingDataFlow: Flow<PagingData<EarthquakeInfo>> = uiState
        .map { it.filterState }
        .distinctUntilChanged()
        .debounce(1200)
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


}