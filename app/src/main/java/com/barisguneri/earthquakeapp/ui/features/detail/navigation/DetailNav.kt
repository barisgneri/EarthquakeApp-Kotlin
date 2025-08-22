package com.barisguneri.earthquakeapp.ui.features.detail.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.barisguneri.earthquakeapp.ui.features.detail.EarthquakeDetailScreen
import com.barisguneri.earthquakeapp.ui.features.detail.EarthquakeDetailViewModel
import com.barisguneri.earthquakeapp.ui.navigation.MainScreen

data class DetailNavActions(
    val navigateToBack: () -> Unit
) {
    companion object {
        val default: DetailNavActions
            get() = DetailNavActions(
                navigateToBack = {}
            )
    }
}

fun NavGraphBuilder.detailScreen(actions: DetailNavActions) {
    composable<MainScreen.Detail> {
        val viewModel: EarthquakeDetailViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val uiEffect = viewModel.uiEffect
        EarthquakeDetailScreen(
            uiState = uiState,
            uiEffect = uiEffect,
            uiAction = viewModel::onAction,
            navActions = actions
        )
    }
}