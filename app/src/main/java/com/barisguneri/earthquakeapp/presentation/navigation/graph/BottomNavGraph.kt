package com.barisguneri.earthquakeapp.presentation.navigation.graph

import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.barisguneri.earthquakeapp.presentation.features.earthquakeList.EarthquakeListScreen
import com.barisguneri.earthquakeapp.presentation.features.map.MapScreen
import com.barisguneri.earthquakeapp.presentation.navigation.BottomScreen
import com.barisguneri.earthquakeapp.presentation.navigation.MainScreen
import com.barisguneri.earthquakeapp.presentation.navigation.bottom.EarthquakeBottomBar

fun NavGraphBuilder.bottomNavGraph(
    navController: NavHostController,
    startDestination: BottomScreen = BottomScreen.HomeList,
) {
    composable<MainScreen.BottomNavGraph> {
        val bottomNavController = rememberNavController()
        Scaffold(
            bottomBar = {
                EarthquakeBottomBar(navController = bottomNavController)
            }
        ) { innerPadding ->
            NavHost(
                navController = bottomNavController,
                startDestination = startDestination,
                modifier = Modifier
                    .padding(innerPadding)
                    .imePadding()
            ) {
                composable<BottomScreen.HomeList> {
                    EarthquakeListScreen(
                        onNavigateToEarthquakeDetail = { id ->
                            navController.navigate(route = MainScreen.Detail(id))
                        }
                    )
                }
                composable<BottomScreen.Map> {
                    MapScreen(
                        onNavigateToEarthquakeDetail = { id ->
                            navController.navigate(route = MainScreen.Detail(id))
                        }
                    )
                }
            }
        }
    }
}