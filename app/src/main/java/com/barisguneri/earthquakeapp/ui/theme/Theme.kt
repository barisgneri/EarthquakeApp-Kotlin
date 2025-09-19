package com.barisguneri.earthquakeapp.ui.theme

import android.app.Activity
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

val LocalActivity = staticCompositionLocalOf<Activity?> { null }

object AppTheme {
    val colors: AppColor
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val padding: AppPadding
        @Composable
        @ReadOnlyComposable
        get() = LocalPadding.current

    val fontSize: AppFontSize
        @Composable
        @ReadOnlyComposable
        get() = LocalFontSize.current

    val typography: AppTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val icons: AppIcons
        @Composable
        @ReadOnlyComposable
        get() = LocalIcons.current

    val dimens: AppDimenSize
        @Composable
        @ReadOnlyComposable
        get() = LocalDimenSize.current
}


@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) darkColors() else lightColors()

    CompositionLocalProvider(
        LocalColors provides colors,
        LocalPadding provides Padding,
        LocalFontSize provides FontSize,
        LocalTypography provides Typography,
        LocalIcons provides AppIcons(),
        LocalDimenSize provides Dimens
    ) {
        content()
    }
}