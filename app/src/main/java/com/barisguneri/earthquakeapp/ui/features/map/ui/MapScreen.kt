package com.barisguneri.earthquakeapp.ui.features.map.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.barisguneri.earthquakeapp.R
import com.barisguneri.earthquakeapp.core.common.CollectWithLifecycle
import com.barisguneri.earthquakeapp.core.presentation.ErrorView
import com.barisguneri.earthquakeapp.core.presentation.LoadingView
import com.barisguneri.earthquakeapp.domain.model.EarthquakeInfo
import com.barisguneri.earthquakeapp.domain.model.MapMarkerData
import com.barisguneri.earthquakeapp.ui.features.earthquakeList.viewmodel.EarthquakeListContract
import com.barisguneri.earthquakeapp.ui.features.map.viewmodel.MapContract
import com.barisguneri.earthquakeapp.ui.features.map.ui.component.MapScreenAppBar
import com.barisguneri.earthquakeapp.ui.features.map.ui.component.MapView
import com.barisguneri.earthquakeapp.ui.features.map.navigaiton.MapNavActions
import com.barisguneri.earthquakeapp.ui.theme.AppTheme
import com.barisguneri.earthquakeapp.ui.theme.AppTheme.padding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.osmdroid.util.GeoPoint

@Composable
fun MapScreen(
    uiState: MapContract.UiState,
    earthquakePinList: List<EarthquakeInfo>,
    uiEffect: Flow<MapContract.UiEffect>,
    onAction: (MapContract.UiAction) -> Unit,
    onMapAction: (EarthquakeListContract.UiAction) -> Unit,
    navActions: MapNavActions,
) {

    uiEffect.CollectWithLifecycle { effect ->
        when (effect) {
            is MapContract.UiEffect.ShowToast -> {}//Todo: Toast
            is MapContract.UiEffect.NavigateToDetail -> navActions.navigateToDetail(effect.earthquakeId)
            is MapContract.UiEffect.NavigateToBack -> navActions.navigateToBack()
        }
    }

    when {
        uiState.isLoading -> LoadingView(modifier = Modifier.fillMaxSize())
        uiState.error != null -> ErrorView(
            errorType = uiState.error,
            modifier = Modifier.fillMaxSize(),
            onRetry = { onAction(MapContract.UiAction.Retry) }
        )

        else -> {
            MapContent(
                uiState = uiState,
                earthquakePinList = earthquakePinList,
                onAction = onAction,
                onMapAction = onMapAction
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MapContent(
    uiState: MapContract.UiState,
    earthquakePinList: List<EarthquakeInfo>,
    onAction: (MapContract.UiAction) -> Unit,
    onMapAction: (EarthquakeListContract.UiAction) -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(1.dp)
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        uiState.earthquake?.let { earthquakeList ->

        }
        MapViewContent(
            earthquakeList = earthquakePinList,
            onAction = onAction,
            onMapAction = onMapAction
        )

        MapScreenAppBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .zIndex(1f)
                .statusBarsPadding(),
            onFilterClick = { onAction(MapContract.UiAction.OnFilterClick) },
            onSearchClick = { onAction(MapContract.UiAction.OnFilterClick) }
        )

        IconButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = padding.dimension8, end = padding.dimension8)
                .size(50.dp),
            onClick = {},
        ) {
            Icon(
                painter = painterResource(R.drawable.gps_icon48),
                contentDescription = "Konumum Icon"
            )
        }
    }
}


@Composable
private fun MapViewContent(
    earthquakeList: List<EarthquakeInfo>,
    onAction: (MapContract.UiAction) -> Unit,
    onMapAction: (EarthquakeListContract.UiAction) -> Unit
) {
    MapView(
        modifier = Modifier,
        markersData = earthquakeList.map { detail ->
            MapMarkerData(
                position = GeoPoint(
                    detail.location.lat,
                    detail.location.lng
                ),
                title = detail.title,
                depth = detail.magnitude.toString(),
                dateTime = detail.dateTime,
                earthquakeId = detail.id,
                magnitude = detail.magnitude
            )
        },
        onButtonClick = { earthquakeId ->
            onAction(MapContract.UiAction.OnEarthquakeClick(earthquakeId))
        },
        onAction = onMapAction
    )
}

@Preview(showBackground = true)
@Composable
fun MapScreenPreview(@PreviewParameter(MapScreenPreviewProvider::class) uiState: MapContract.UiState) {
    AppTheme {
        MapScreen(
            uiState = uiState,
            earthquakePinList = emptyList(),
            uiEffect = emptyFlow(),
            onAction = {},
            onMapAction = {},
            navActions = MapNavActions.default,
        )
    }
}
