package com.barisguneri.earthquakeapp.core.presentation

import android.app.Activity
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowInsetsControllerCompat

@Composable
fun SystemBarsScrim(
    barColor: Color,
    lightBarIcons: Boolean = false,
    lightNavIcons: Boolean = false,
) {
    val view = LocalView.current
    if (view.isInEditMode) return
    val window = (LocalView.current.context as Activity).window

    SideEffect {
        WindowInsetsControllerCompat(window, window.decorView).apply {
            isAppearanceLightStatusBars = lightBarIcons
            isAppearanceLightNavigationBars = lightNavIcons
        }
    }
    Box(Modifier.fillMaxSize()) {
        Spacer(
            Modifier
                .windowInsetsTopHeight(WindowInsets.statusBars)
                .fillMaxWidth()
                .background(color = barColor)
        )
        Spacer(
            Modifier
                .windowInsetsBottomHeight(WindowInsets.navigationBars)
                .fillMaxWidth()
                .background(color = barColor)
                .align(Alignment.BottomCenter)
        )
    }
}