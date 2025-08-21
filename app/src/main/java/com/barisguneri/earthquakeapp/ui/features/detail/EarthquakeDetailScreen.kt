package com.barisguneri.earthquakeapp.ui.features.detail

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.barisguneri.earthquakeapp.domain.model.EarthquakeDetail
import com.barisguneri.earthquakeapp.core.presentation.ErrorView
import com.barisguneri.earthquakeapp.core.presentation.LoadingView

@Composable
fun EarthquakeDetailScreen(
    viewModel: EarthquakeDetailViewModel = hiltViewModel(),
    earthquakeId: String?,
    onNavigateBack: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    EarthquakeDetailContent(
        detailState = state,
        onEvent = viewModel::onEvent,
    )
}

@Composable
fun EarthquakeDetailContent(
    detailState: EarthquakeDetailScreenState,
    onEvent: (EarthquakeDetailScreenEvent) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .scrollable(
                enabled = true,
                state = ScrollState(0),
                orientation = Orientation.Horizontal
            )
    ) {
        when {
            detailState.isLoading -> {
                LoadingView()
            }

            detailState.error != null -> {
                ErrorView(
                    errorType = detailState.error,
                    onRetry = { onEvent(EarthquakeDetailScreenEvent.Retry) })
            }

            detailState.earthquake != null -> {
                MapDetail(modifier = Modifier, detail = listOf(detailState.earthquake) )
            }
        }
    }
}

@Composable
fun MapDetail(modifier: Modifier, detail: List<EarthquakeDetail>) {

/*    MapView(modifier = modifier.fillMaxSize(), onButtonClick = {}, markersData = detail.map { detail ->
        MapMarkerData(
            position = GeoPoint(
                detail.location.lat,
                detail.location.long
            ),
            title = detail.title,
            magnitude = detail.magnitude,
            depth = detail.depthInfo,
            dateTime = detail.dateTime,
            earthquakeId = detail.id
        )
    }
    )*/
}