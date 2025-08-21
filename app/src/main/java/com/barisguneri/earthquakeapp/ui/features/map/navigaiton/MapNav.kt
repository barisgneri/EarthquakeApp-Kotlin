package com.barisguneri.earthquakeapp.ui.features.map.navigaiton

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.barisguneri.earthquakeapp.ui.features.map.MapScreen
import com.barisguneri.earthquakeapp.ui.features.map.MapViewModel
import com.barisguneri.earthquakeapp.ui.navigation.BottomScreen


data class MapNavActions(
    val navigateToBack: () -> Unit,
    val navigateToDetail: (String) -> Unit,
) {
    companion object {
        val default: MapNavActions
            get() = MapNavActions(
                navigateToBack = {},
                navigateToDetail = {}
            )
    }
}
fun NavGraphBuilder.mapScreen(actions: MapNavActions){
    composable<BottomScreen.Map> {
        val viewModel: MapViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val uiEffect = viewModel.uiEffect
        MapScreen(
            uiState = uiState,
            uiEffect = uiEffect,
            onAction = viewModel::onAction,
            navActions = actions
        )
    }
}