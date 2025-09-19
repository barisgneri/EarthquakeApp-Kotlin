package com.barisguneri.earthquakeapp.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.barisguneri.earthquakeapp.R

internal val LocalIcons = staticCompositionLocalOf { AppIcons() }

@Stable
class AppIcons {
    val backArrow: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.arrow_back_48)
    val errorIcon: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.error_icon)
}