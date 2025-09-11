package com.barisguneri.earthquakeapp.ui.features.splash

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.barisguneri.earthquakeapp.ui.navigation.MainScreen

data class SplashNavActions(
    val navigateToHome: () -> Unit,
) {
    companion object {
        val default: SplashNavActions
            get() = SplashNavActions(
                navigateToHome = {}
            )
    }
}

fun NavGraphBuilder.splashScreen(actions: SplashNavActions) {
    composable<MainScreen.Splash> {
        SplashScreen(navActions = actions)
    }
}