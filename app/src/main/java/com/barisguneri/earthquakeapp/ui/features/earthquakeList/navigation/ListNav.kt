package com.barisguneri.earthquakeapp.ui.features.earthquakeList.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.barisguneri.earthquakeapp.ui.features.earthquakeList.ui.EarthquakeListScreen
import com.barisguneri.earthquakeapp.ui.main.SharedViewModel
import com.barisguneri.earthquakeapp.ui.navigation.BottomScreen
import com.barisguneri.earthquakeapp.ui.navigation.MainScreen

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

fun NavGraphBuilder.listScreen(navController: NavHostController, actions: ListNavActions){
    composable<BottomScreen.HomeList> {
        val parentEntry = remember(it) {
            navController.getBackStackEntry(MainScreen.BottomNavGraph)
        }
        val viewModel: SharedViewModel = hiltViewModel(parentEntry)
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val uiEffect = viewModel.uiEffect
        val lazyPagingItems = viewModel.pagingDataFlow.collectAsLazyPagingItems()
        EarthquakeListScreen(
            uiState = uiState,
            uiEffect = uiEffect,
            onAction = viewModel::onAction,
            navActions = actions,
            pagingItems = lazyPagingItems
        )
    }
}