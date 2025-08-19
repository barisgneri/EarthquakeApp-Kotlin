package com.barisguneri.earthquakeapp.ui.features.splash

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.barisguneri.earthquakeapp.ui.navigation.MainScreen

fun NavGraphBuilder.splashNavigation(
    navigateToHome: () -> Unit
){
    composable<MainScreen.Splash> {
        SplashScreen(
            navigateToHome = navigateToHome
        )
    }
}