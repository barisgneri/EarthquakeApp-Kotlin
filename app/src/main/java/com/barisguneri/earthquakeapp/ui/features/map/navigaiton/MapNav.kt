package com.barisguneri.earthquakeapp.ui.features.map.navigaiton

import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.barisguneri.earthquakeapp.ui.features.map.ui.MapScreen
import com.barisguneri.earthquakeapp.ui.main.SharedViewModel
import com.barisguneri.earthquakeapp.ui.navigation.BottomScreen
import com.barisguneri.earthquakeapp.ui.navigation.MainScreen


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
fun NavGraphBuilder.mapScreen(navController: NavHostController, actions: MapNavActions){
    composable<BottomScreen.Map> {
        val parentEntry = remember(it) {
            navController.getBackStackEntry(MainScreen.BottomNavGraph)
        }
        val viewModel: SharedViewModel = hiltViewModel(parentEntry)
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val mapPins by viewModel.visibleMapPins.collectAsStateWithLifecycle()
        val uiEffect = viewModel.uiEffect
        MapScreen(
            uiState = uiState,
            mapPinList = mapPins,
            uiEffect = uiEffect,
            onAction = viewModel::onAction,
            navActions = actions
        )
    }
}