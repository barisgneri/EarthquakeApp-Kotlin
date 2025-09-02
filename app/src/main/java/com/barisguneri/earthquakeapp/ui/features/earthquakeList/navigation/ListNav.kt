package com.barisguneri.earthquakeapp.ui.features.earthquakeList.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.barisguneri.earthquakeapp.ui.features.earthquakeList.EarthquakeListScreen
import com.barisguneri.earthquakeapp.ui.features.earthquakeList.EarthquakeViewModel
import com.barisguneri.earthquakeapp.ui.navigation.BottomScreen

data class ListNavActions(
    val navigateToDetail: (String) -> Unit,
){
    companion object {
        val default: ListNavActions
            get() =ListNavActions(
                navigateToDetail = {}
            )
    }
}

fun NavGraphBuilder.listScreen(actions: ListNavActions){
    composable<BottomScreen.HomeList> {
        val viewModel: EarthquakeViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val uiEffect = viewModel.uiEffect
        EarthquakeListScreen(
            uiState = uiState,
            uiEffect = uiEffect,
            onAction = viewModel::onAction,
            navActions = actions
        )
    }
}