package com.barisguneri.earthquakeapp

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.barisguneri.earthquakeapp.view.ListScreen
import com.barisguneri.earthquakeapp.view.MapViewScreen
import com.barisguneri.earthquakeapp.viewmodel.EarthquakeViewModel

@Composable
fun ScreenNavigation() {

    val navController = rememberNavController()
    val viewModel: EarthquakeViewModel = viewModel()

    NavHost(navController = navController, startDestination = LIST_SCREEN ){
        composable(LIST_SCREEN){
            ListScreen(navController = navController, viewModel = viewModel)
        }
        composable(MAP_SCREEN){
            MapViewScreen(navController = navController, viewModel)
        }
    }
}

const val LIST_SCREEN = "list_screen"
const val MAP_SCREEN = "map_screen"