package com.barisguneri.earthquakeapp.ui.features.earthquakeList

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.barisguneri.earthquakeapp.core.common.ErrorType
import com.barisguneri.earthquakeapp.core.domain.delegate.MVI
import com.barisguneri.earthquakeapp.domain.model.EarthquakeInfo
import com.barisguneri.earthquakeapp.domain.useCase.GetEarthquakesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.barisguneri.earthquakeapp.ui.features.earthquakeList.EarthquakeListContract.UiState
import com.barisguneri.earthquakeapp.ui.features.earthquakeList.EarthquakeListContract.UiAction
import com.barisguneri.earthquakeapp.ui.features.earthquakeList.EarthquakeListContract.UiEffect
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map


@HiltViewModel
class EarthquakeViewModel @Inject constructor(
    private val getEarthquakesUseCase: GetEarthquakesUseCase
) : MVI<UiState, UiEffect, UiAction>(UiState()) {

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
                getEarthquakesUseCase(currentFilters)
        }
        .cachedIn(viewModelScope)

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
                    copy(filterState = this.filterState.copy(searchQuery = uiAction.query))
                }
            }
            is UiAction.UpdateMagnitude -> {
                updateState {
                    copy(filterState = this.filterState.copy(minMagnitude = uiAction.magnitude))
                }
            }
            is UiAction.UpdateTimeRange -> {
                updateState {
                    copy(filterState = this.filterState.copy(timeRange = uiAction.range))
                }
            }
            is UiAction.UpdateSortBy -> {
                updateState {
                    copy(filterState = this.filterState.copy(sortBy = uiAction.option))
                }
            }
        }
    }
}