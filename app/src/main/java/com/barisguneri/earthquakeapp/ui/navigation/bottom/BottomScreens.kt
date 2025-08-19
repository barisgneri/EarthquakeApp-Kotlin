package com.barisguneri.earthquakeapp.ui.navigation.bottom

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.barisguneri.earthquakeapp.R
import com.barisguneri.earthquakeapp.ui.navigation.BottomScreen


data class BottomScreens<T : Any>(val route: T, val icon: Int, val name: String)

object BottomNavigationItems {
    fun getItems() = listOf(
        BottomScreens(BottomScreen.HomeList, R.drawable.ic_launcher_background, "List"),
        BottomScreens(BottomScreen.Map, R.drawable.ic_launcher_background, "Map"),

    )
}

fun <T : Any> NavHostController.navigateReorderBackStack(
    route: T
) {
    this@navigateReorderBackStack.navigate(route) {
        launchSingleTop = true
        popUpTo(this@navigateReorderBackStack.graph.findStartDestination().id) {
            saveState = true
        }
        restoreState = true
    }
}