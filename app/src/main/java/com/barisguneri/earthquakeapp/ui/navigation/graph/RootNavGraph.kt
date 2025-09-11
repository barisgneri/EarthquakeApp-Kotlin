package com.barisguneri.earthquakeapp.ui.navigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.barisguneri.earthquakeapp.ui.navigation.BottomScreen
import com.barisguneri.earthquakeapp.ui.navigation.MainScreen

@Composable
fun RootNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = Modifier.then(modifier),
        navController = navController,
        startDestination = MainScreen.Splash,
    ) {
        mainNavGraph(navController = navController)
        bottomNavGraph(navController = navController)
    }
}