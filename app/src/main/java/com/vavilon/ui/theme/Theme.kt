package com.vavilon.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = VavilonColors(
    backgroundUI = Midnight,
    income1 = ForestGreen,
    income2 = LimeGreen,
    income3 = MediumSeaGreen,
    income4 = SpringGreen,
    income5 = LightGreen,
    expense1 = FireBrick,
    expense2 = IndianRed,
    expense3 = OrangeRed,
    expense4 = Tomato,
    savings1 = MediumBlue,
    savings2 = RoyalBlue,
    savings3 = DodgerBlue,
    savings4 = SteelBlue,
    primaryText = Gold,
    helpText = DarkBlue,
    darkText = Forest,
    lightText = Mist,
    backgroundIcon = Gold,
    primaryElement = Mist,
    secondaryElement = DeepWater,
    error = Crimson,
    confirmButton = LightGreen,
    canselButton = FireBrick,
    backgroundNav = Gold,
    helpElement = SkyBlue
)

private val LightColorScheme = VavilonColors(
    backgroundUI = Midnight,
    income1 = ForestGreen,
    income2 = LimeGreen,
    income3 = MediumSeaGreen,
    income4 = SpringGreen,
    income5 = LightGreen,
    expense1 = FireBrick,
    expense2 = IndianRed,
    expense3 = OrangeRed,
    expense4 = Tomato,
    savings1 = MediumBlue,
    savings2 = RoyalBlue,
    savings3 = DodgerBlue,
    savings4 = SteelBlue,
    primaryText = Gold,
    helpText = DarkBlue,
    darkText = Forest,
    lightText = Mist,
    backgroundIcon = Bronze,
    primaryElement = Mist,
    secondaryElement = DeepWater,
    error = Crimson,
    confirmButton = LightGreen,
    canselButton = FireBrick,
    backgroundNav = Gold,
    helpElement = SkyBlue
)

@Composable
fun VavilonTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    ProvideVavilonColors(colors) {
        MaterialTheme(
            typography = Typography,
            content = content
        )
    }
}

object VavilonTheme {
    val colors: VavilonColors
        @Composable
        get() = LocalVavilonColors.current
}

@Immutable
data class VavilonColors(
    val backgroundUI: Color,
    val income1: Color,
    val income2: Color,
    val income3: Color,
    val income4: Color,
    val income5: Color,
    val expense1: Color,
    val expense2: Color,
    val expense3: Color,
    val expense4: Color,
    val savings1: Color,
    val savings2: Color,
    val savings3: Color,
    val savings4: Color,
    val primaryText: Color,
    val helpText: Color,
    val darkText: Color,
    val lightText: Color,
    val backgroundIcon: Color,
    val primaryElement: Color,
    val secondaryElement: Color,
    val error: Color,
    val confirmButton: Color,
    val canselButton: Color,
    val backgroundNav: Color,
    val helpElement: Color
)

@Composable
fun ProvideVavilonColors(
    colors: VavilonColors,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalVavilonColors provides colors, content = content)
}

private val LocalVavilonColors = staticCompositionLocalOf<VavilonColors> {
    error("No Vavilon Colors Provided")
}