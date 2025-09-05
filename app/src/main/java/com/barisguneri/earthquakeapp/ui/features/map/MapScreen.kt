package com.barisguneri.earthquakeapp.ui.features.map

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.captionBarPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeGesturesPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.barisguneri.earthquakeapp.core.presentation.LoadingView
import com.barisguneri.earthquakeapp.domain.model.EarthquakeInfo
import com.barisguneri.earthquakeapp.domain.model.MapMarkerData
import com.barisguneri.earthquakeapp.ui.features.map.component.MapScreenAppBar
import com.barisguneri.earthquakeapp.ui.features.map.component.MapView
import com.barisguneri.earthquakeapp.ui.features.map.navigaiton.MapNavActions
import com.barisguneri.earthquakeapp.ui.theme.AppTheme
import com.barisguneri.earthquakeapp.ui.theme.AppTheme.padding
import com.barisguneri.earthquakeapp.ui.theme.LocalPadding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.osmdroid.util.GeoPoint

@Composable
fun MapScreen(
    uiState: MapContract.UiState,
    uiEffect: Flow<MapContract.UiEffect>,
    onAction: (MapContract.UiAction) -> Unit,
    navActions: MapNavActions,
) {

    uiEffect.CollectWithLifecycle { effect ->
        when (effect) {
            is MapContract.UiEffect.ShowToast -> {}//Todo: Toast
            is MapContract.UiEffect.NavigateToDetail -> navActions.navigateToDetail(effect.earthquakeId)
        }
    }

    when {
        uiState.isLoading -> LoadingView()
        else -> {
            MapContent(
                uiState = uiState,
                onAction = onAction,
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MapContent(
    uiState: MapContract.UiState,
    onAction: (MapContract.UiAction) -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(1.dp)
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        // MapViewContent en altta
        uiState.earthquake?.let { earthquakeList ->
            MapViewContent(
                earthquakeList = earthquakeList,
                onAction = onAction
            )
        }

        // AppBar en üstte
        MapScreenAppBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .zIndex(1f)
                .statusBarsPadding(),
            onFilterClick = {},
            onSearchClick = {}
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
) {
    MapView(
        modifier = Modifier,
        markersData = earthquakeList.map { detail ->
            MapMarkerData(
                position = GeoPoint(
                    detail.location.lat,
                    detail.location.long
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
        }
    )
}

@Preview(showBackground = true)
@Composable
fun MapScreenPreview(@PreviewParameter(MapScreenPreviewProvider::class) uiState: MapContract.UiState) {
    AppTheme {
        MapScreen(
            uiState = uiState,
            uiEffect = emptyFlow(),
            onAction = {},
            navActions = MapNavActions.default,
        )
    }
}
