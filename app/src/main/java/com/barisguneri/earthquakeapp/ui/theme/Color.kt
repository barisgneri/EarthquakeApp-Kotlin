package com.barisguneri.earthquakeapp.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

internal val LocalColors = staticCompositionLocalOf { lightColors() }

val primaryLight = Color(0xFF007BFF)
val onPrimaryLight = Color(0xFFFFFFFF)
val errorLight = Color(0xFFFCBAAD)
val buttonLight = Color(0xFF007BFF)
val textLight = Color(0xFF000000)
val onTextLight = Color(0xFFFFFFFF)
val onBackgroundLight = Color(0xFF111C22)
val textSecondaryLight = Color(0xFF242424)
val backgroundLight = Color(0xfff9fafb)
val cardBackgroundLight = Color(0xFFFFFFFF)
val grayLight = Color(0xFFD3D3D3)
val whiteBlackLight = Color(0xFFFFFFFF)
val surfaceLight = Color(0xFFF5F5F5)
val primarySurfaceLight = Color(0xFFE3F2FD)
val secondaryWhite = Color.White.copy(alpha = 0.3f)

val primaryDark = Color(0xFF007BFF)
val onPrimaryDark = Color(0xFFFFFFFF)
val errorDark = Color(0xFFD32F2F)
val buttonDark = Color(0xFF007BFF)
val textDark = Color(0xFFFFFFFF)
val onBackgroundDark = Color(0xfff9fafb)
val onTextDark = Color(0xFF000000)
val textSecondaryDark = Color(0xFFAFAFAF)
val backgroundDark = Color(0xFF111C22)
val cardBackgroundDark = Color(0xFF444444)
val grayDark = Color(0xFF777777)
val whiteBlackDark = Color(0xFF000000)
val surfaceDark = Color(0xFF333333)
val primarySurfaceDark = Color(0xFF1A2B3D)
val primaryBlue = Color(0xFF155ECB)

fun lightColors(): AppColor = AppColor(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    error = errorLight,
    button = buttonLight,
    text = textLight,
    onText = onTextLight,
    onBackground = onBackgroundLight,
    textSecondary = textSecondaryLight,
    background = backgroundLight,
    cardBackground = cardBackgroundLight,
    gray = grayLight,
    whiteBlack = whiteBlackLight,
    surface = surfaceLight,
    primarySurface = primarySurfaceLight,
    secondaryWhite = secondaryWhite,
    primaryBlue = primaryBlue,
)

fun darkColors(): AppColor = AppColor(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    error = errorDark,
    button = buttonDark,
    text = textDark,
    onText = onTextDark,
    onBackground = onBackgroundDark,
    textSecondary = textSecondaryDark,
    background = backgroundDark,
    cardBackground = cardBackgroundDark,
    gray = grayDark,
    whiteBlack = whiteBlackDark,
    surface = surfaceDark,
    primarySurface = primarySurfaceDark,
    primaryBlue = primaryBlue,
    secondaryWhite = secondaryWhite,
)

@Immutable
data class AppColor(
    val primary: Color,
    val onPrimary: Color,
    val error: Color,
    val button: Color,
    val text: Color,
    val onText: Color,
    val onBackground: Color,
    val textSecondary: Color,
    val background: Color,
    val cardBackground: Color,
    val black: Color = Color.Black,
    val red: Color = Color.Red,
    val white: Color = Color.White,
    val gray: Color,
    val whiteBlack: Color,
    val lightGray: Color = Color.LightGray,
    val surface: Color,
    val primarySurface: Color,
    val primaryBlue : Color,
    val secondaryWhite : Color,
    val magnitudeTextRed : Color = Color(0xffe7000b),
    val magnitudeTextOrange : Color = Color(0xFFffb86a),
    val magnitudeTextYellow : Color = Color(0xFFffdf20),
    val magnitudeTextGreen : Color = Color(0xFF7bf1a8),
    val magnitudeBackgroundRed : Color = Color(0xff82181a),
    val magnitudeBackgroundOrange : Color = Color(0xFF7e2a0c),
    val magnitudeBackgroundYellow : Color = Color(0xFF733e0a),
    val magnitudeBackgroundGreen : Color = Color(0xFF0d542b),
)