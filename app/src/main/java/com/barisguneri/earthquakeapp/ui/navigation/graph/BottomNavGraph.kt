package com.barisguneri.earthquakeapp.ui.navigation.graph

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
import com.barisguneri.earthquakeapp.ui.features.earthquakeList.EarthquakeListScreen
import com.barisguneri.earthquakeapp.ui.features.map.MapScreen
import com.barisguneri.earthquakeapp.ui.features.map.navigaiton.MapNavActions
import com.barisguneri.earthquakeapp.ui.features.map.navigaiton.mapScreen
import com.barisguneri.earthquakeapp.ui.navigation.BottomScreen
import com.barisguneri.earthquakeapp.ui.navigation.MainScreen
import com.barisguneri.earthquakeapp.ui.navigation.bottom.EarthquakeBottomBar

fun NavGraphBuilder.bottomNavGraph(
    navController: NavHostController,
    startDestination: BottomScreen = BottomScreen.Map,
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
                mapScreen(
                    MapNavActions(
                        navigateToDetail = { id ->
                            navController.navigate(route = MainScreen.Detail(id)){
                            }
                        },
                        navigateToBack = { navController.popBackStack() },
                    )
                )
            }
        }
    }
}