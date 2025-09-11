package com.barisguneri.earthquakeapp.ui.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.barisguneri.earthquakeapp.ui.features.detail.navigation.DetailNavActions
import com.barisguneri.earthquakeapp.ui.features.detail.navigation.detailScreen
import com.barisguneri.earthquakeapp.ui.features.splash.SplashNavActions
import com.barisguneri.earthquakeapp.ui.features.splash.splashScreen
import com.barisguneri.earthquakeapp.ui.navigation.MainScreen

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
) {
    splashScreen(
        actions = SplashNavActions(
            navigateToHome = {
                navController.navigate(MainScreen.BottomNavGraph) {
                    popUpTo(MainScreen.Splash) {
                        inclusive = true
                    }
                }
            }
        )
    )
    detailScreen(
        actions = DetailNavActions(
            navigateToBack = { navController.popBackStack() }
        )
    )
}