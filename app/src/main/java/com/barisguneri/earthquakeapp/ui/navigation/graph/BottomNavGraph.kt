package com.barisguneri.earthquakeapp.ui.navigation.graph

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.barisguneri.earthquakeapp.ui.features.earthquakeList.navigation.ListNavActions
import com.barisguneri.earthquakeapp.ui.features.earthquakeList.navigation.listScreen
import com.barisguneri.earthquakeapp.ui.features.map.navigaiton.MapNavActions
import com.barisguneri.earthquakeapp.ui.features.map.navigaiton.mapScreen
import com.barisguneri.earthquakeapp.ui.navigation.BottomScreen
import com.barisguneri.earthquakeapp.ui.navigation.MainScreen
import com.barisguneri.earthquakeapp.ui.navigation.bottom.EarthquakeBottomBar

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
                    .padding(
                        start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                        end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
                        bottom = innerPadding.calculateBottomPadding()
                    )


            ) {
                listScreen(
                    navController = navController,
                    ListNavActions(
                        navigateToDetail = { id ->
                            navController.navigate(route = MainScreen.Detail(id)){}
                        }
                    )
                )
                mapScreen(
                    navController = navController,
                    MapNavActions(
                        navigateToDetail = { id ->
                            navController.navigate(route = MainScreen.Detail(id)) {
                            }
                        },
                        navigateToBack = {},
                    )
                )
            }
        }
    }
}