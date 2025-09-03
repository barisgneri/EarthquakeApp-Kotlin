package com.barisguneri.earthquakeapp.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal val LocalDimenSize = staticCompositionLocalOf<AppDimenSize> {
    error("No default dimen size provided")
}

val Dimens = AppDimenSize(
    dp0 = 0.dp,
    dp1 = 1.dp,
    dp2 = 2.dp,
    dp4 = 4.dp,
    dp6 = 6.dp,
    dp8 = 8.dp,
    dp10 = 10.dp,
    dp12 = 12.dp,
    dp14 = 14.dp,
    dp16 = 16.dp,
    dp18 = 18.dp,
    dp20 = 20.dp,
    dp22 = 22.dp,
    dp24 = 24.dp,
    dp26 = 26.dp,
    dp28 = 28.dp,
    dp30 = 30.dp,
    dp32 = 32.dp,
    dp34 = 34.dp,
    dp36 = 36.dp,
    dp38 = 38.dp,
    dp40 = 40.dp,
    dp42 = 42.dp,
    dp44 = 44.dp,
    dp46 = 46.dp,
    dp48 = 48.dp,
    dp50 = 50.dp,
    dp55 = 55.dp
)

@Immutable
data class AppDimenSize(
    val dp0: Dp,
    val dp1: Dp,
    val dp2: Dp,
    val dp4: Dp,
    val dp6: Dp,
    val dp8: Dp,
    val dp10: Dp,
    val dp12: Dp,
    val dp14: Dp,
    val dp16: Dp,
    val dp18: Dp,
    val dp20: Dp,
    val dp22: Dp,
    val dp24: Dp,
    val dp26: Dp,
    val dp28: Dp,
    val dp30: Dp,
    val dp32: Dp,
    val dp34: Dp,
    val dp36: Dp,
    val dp38: Dp,
    val dp40: Dp,
    val dp42: Dp,
    val dp44: Dp,
    val dp46: Dp,
    val dp48: Dp,
    val dp50: Dp,
    val dp55: Dp
)
