package com.barisguneri.earthquakeapp.presentation.features.splash

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.barisguneri.earthquakeapp.presentation.navigation.MainScreen

fun NavGraphBuilder.splashNavigation(
    navigateToHome: () -> Unit
){
    composable<MainScreen.Splash> {
        SplashScreen(
            navigateToHome = navigateToHome
        )
    }
}