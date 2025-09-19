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
import com.barisguneri.earthquakeapp.ui.components.ErrorView
import com.barisguneri.earthquakeapp.ui.components.LoadingView
import com.barisguneri.earthquakeapp.domain.model.EarthquakeInfo
import com.barisguneri.earthquakeapp.domain.model.FilterState
import com.barisguneri.earthquakeapp.domain.model.MapMarkerData
import com.barisguneri.earthquakeapp.ui.features.map.ui.component.MapScreenAppBar
import com.barisguneri.earthquakeapp.ui.features.map.ui.component.MapView
import com.barisguneri.earthquakeapp.ui.features.map.navigaiton.MapNavActions
import com.barisguneri.earthquakeapp.ui.theme.AppTheme
import com.barisguneri.earthquakeapp.ui.theme.AppTheme.padding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.osmdroid.util.GeoPoint
import com.barisguneri.earthquakeapp.ui.main.SharedContract.UiAction
import com.barisguneri.earthquakeapp.ui.main.SharedContract.UiEffect
import com.barisguneri.earthquakeapp.ui.main.SharedContract.UiState

@Composable
fun MapScreen(
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit,
    navActions: MapNavActions,
    mapPinList: List<EarthquakeInfo>,
) {

    uiEffect.CollectWithLifecycle { effect ->
        when (effect) {
            is UiEffect.ShowToast -> {}//Todo: Toast
            is UiEffect.NavigateToDetail -> navActions.navigateToDetail(effect.earthquakeId)
        }
    }

    when {
        uiState.isLoading -> LoadingView(modifier = Modifier.fillMaxSize())
        uiState.error != null -> ErrorView(
            errorType = uiState.error,
            modifier = Modifier.fillMaxSize(),
            onRetry = { onAction(UiAction.Retry) }
        )

        else -> {
            MapContent(
                filterState = uiState.filterState,
                onAction = onAction,
                mapPinList = mapPinList
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MapContent(
    filterState: FilterState,
    mapPinList: List<EarthquakeInfo>,
    onAction: (UiAction) -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(1.dp)
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        MapViewContent(
            earthquakeList = mapPinList,
            onAction = onAction,
        )

        MapScreenAppBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .zIndex(1f)
                .statusBarsPadding(),
            filterState = filterState,
            onAction = onAction
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
    onAction: (UiAction) -> Unit
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
            onAction(UiAction.OnEarthquakeClick(earthquakeId))
        },
        onAction = onAction
    )
}

@Preview(showBackground = true)
@Composable
fun MapScreenPreview(@PreviewParameter(MapScreenPreviewProvider::class) uiState: UiState) {
    AppTheme {
        MapScreen(
            uiState = uiState,
            mapPinList = emptyList(),
            uiEffect = emptyFlow(),
            onAction = {},
            navActions = MapNavActions.default,
        )
    }
}
