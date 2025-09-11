package com.barisguneri.earthquakeapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.barisguneri.earthquakeapp.presentation.navigation.Screen.LIST_SCREEN
import com.barisguneri.earthquakeapp.presentation.navigation.Screen.MAP_SCREEN
import com.barisguneri.earthquakeapp.presentation.earthquake.ListScreen
import com.barisguneri.earthquakeapp.presentation.view.MapViewScreen
import com.barisguneri.earthquakeapp.presentation.viewmodel.EarthquakeViewModel

@Composable
fun ScreenNavigation() {

    val navController = rememberNavController()
    val viewModel: EarthquakeViewModel = viewModel()

    NavHost(navController = navController, startDestination = LIST_SCREEN ){
        composable(LIST_SCREEN){
            ListScreen()
        }
        composable("$MAP_SCREEN/{earthquakeDetail}"){
            MapViewScreen(navController = navController, viewModel)
        }
    }
}

object Screen {
    const val LIST_SCREEN = "list_screen"
    const val MAP_SCREEN = "map_screen"
}