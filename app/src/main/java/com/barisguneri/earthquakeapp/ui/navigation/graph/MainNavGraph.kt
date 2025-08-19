package com.barisguneri.earthquakeapp.ui.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.barisguneri.earthquakeapp.ui.features.detail.EarthquakeDetailContent
import com.barisguneri.earthquakeapp.ui.features.detail.EarthquakeDetailScreen
import com.barisguneri.earthquakeapp.ui.features.splash.SplashScreen
import com.barisguneri.earthquakeapp.ui.navigation.MainScreen

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
) {
    composable<MainScreen.Splash> {
        SplashScreen {
            navController.navigate(MainScreen.BottomNavGraph) {
                popUpTo(MainScreen.Splash) {
                    inclusive = true
                }
            }
        }
    }
    composable<MainScreen.Detail> {
       EarthquakeDetailScreen(
           earthquakeId = it.id,
           onNavigateBack = { navController.popBackStack() },
       )
    }
}