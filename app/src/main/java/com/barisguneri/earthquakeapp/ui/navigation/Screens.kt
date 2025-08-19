package com.barisguneri.earthquakeapp.ui.navigation

import kotlinx.serialization.Serializable

sealed interface MainScreen {

    @Serializable
    data object Splash : MainScreen

    @Serializable
    data object BottomNavGraph : MainScreen

    @Serializable
    data class Detail(val itemId: String) : MainScreen
}

sealed interface BottomScreen {
    @Serializable
    data object HomeList : BottomScreen
    @Serializable
    data object Map : BottomScreen
}

