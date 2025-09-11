package com.barisguneri.earthquakeapp.ui.features.detail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.barisguneri.earthquakeapp.core.common.CollectWithLifecycle
import com.barisguneri.earthquakeapp.core.presentation.ErrorView
import com.barisguneri.earthquakeapp.core.presentation.LoadingView
import com.barisguneri.earthquakeapp.domain.model.MapMarkerData
import com.barisguneri.earthquakeapp.ui.features.detail.viewmodel.EarthquakeDetailContract.UiState
import com.barisguneri.earthquakeapp.ui.features.detail.viewmodel.EarthquakeDetailContract.UiEffect
import com.barisguneri.earthquakeapp.ui.features.detail.viewmodel.EarthquakeDetailContract.UiAction
import com.barisguneri.earthquakeapp.ui.features.detail.ui.component.DetailMap
import com.barisguneri.earthquakeapp.ui.features.detail.ui.component.DetailTopAppBar
import com.barisguneri.earthquakeapp.ui.features.detail.navigation.DetailNavActions
import com.barisguneri.earthquakeapp.ui.features.detail.ui.component.ClosestAirportContent
import com.barisguneri.earthquakeapp.ui.features.detail.ui.component.ClosestCitiesContent
import com.barisguneri.earthquakeapp.ui.features.map.ui.component.HeaderDetailContent
import com.barisguneri.earthquakeapp.ui.theme.AppTheme
import com.barisguneri.earthquakeapp.ui.theme.AppTheme.colors
import com.barisguneri.earthquakeapp.ui.theme.AppTheme.typography
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.osmdroid.util.GeoPoint

@Composable
fun EarthquakeDetailScreen(
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    uiAction: (UiAction) -> Unit,
    navActions: DetailNavActions
) {

    uiEffect.CollectWithLifecycle { effect ->
        when (effect) {
            is UiEffect.ShowToast -> {}
            is UiEffect.NavigateBack -> navActions.navigateToBack()
        }
    }

    when {
        uiState.isLoading -> LoadingView(modifier = Modifier.fillMaxSize())
        uiState.error != null -> ErrorView(
            errorType = uiState.error,
            onRetry = { uiAction(UiAction.Retry) },
            modifier = Modifier.fillMaxSize()
        )

        else -> {
            EarthquakeDetailContent(
                uiState = uiState,
                uiAction = uiAction
            )
        }
    }
}

@Composable
fun EarthquakeDetailContent(
    uiState: UiState,
    uiAction: (UiAction) -> Unit,
) {
    val scrollState = rememberScrollState()
    Scaffold(
        modifier = Modifier
            .background(colors.background)
            .fillMaxSize(),
        topBar = {
            DetailTopAppBar(
                onBackClick = { uiAction(UiAction.NavigateBack) }
            )
        },
        containerColor = Color.Transparent
    ) { innerPadding ->
        uiState.earthquake?.let { earthquake ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .fillMaxSize()


        ) {
                DetailMap(
                    modifier = Modifier.height(260.dp),
                    markersData = MapMarkerData(
                        position = GeoPoint(
                            earthquake.location.lat,
                            earthquake.location.lng
                        ),
                        title = earthquake.title,
                        depth = earthquake.magnitude.toString(),
                        dateTime = earthquake.dateTime,
                        earthquakeId = earthquake.id,
                        magnitude = earthquake.magnitude
                    )
                )

                Text(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    text = earthquake.title,
                    color = colors.text,
                    style = typography.bodyLargeBold(),
                    textAlign = TextAlign.Center
                )

                HeaderDetailContent(modifier = Modifier.background(colors.background), earthquake = earthquake)
                ClosestAirportContent(airports = earthquake.airports)
                ClosestCitiesContent(closestCities = earthquake.closestCities)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DetailScreenPreviewProvider(@PreviewParameter(EarthquakeDetailScreenPreviewProvider::class) uiState: UiState) {
    AppTheme {
        EarthquakeDetailScreen(
            uiState = uiState,
            uiEffect = flowOf(),
            uiAction = {},
            navActions = DetailNavActions.default
        )
    }
}