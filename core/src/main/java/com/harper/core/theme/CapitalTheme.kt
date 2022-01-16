package com.harper.core.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*

object CapitalTheme {

    val colors: CapitalColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val shapes: CapitalShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalShapes.current

    val typography: CapitalTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current
}

@Composable
fun CapitalTheme(isDark: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) =
    CapitalTheme(
        colors = if (isDark) darkColors() else lightColors(),
        shapes = CapitalShapes(),
        typography = CapitalTypography(),
        content
    )

@Composable
private fun CapitalTheme(
    colors: CapitalColors,
    shapes: CapitalShapes,
    typography: CapitalTypography,
    content: @Composable () -> Unit
) {
    val rememberedColors = remember {
        // Explicitly creating a new object here so we don't mutate the initial [colors]
        // provided, and overwrite the values set in it.
        colors.copy()
    }.apply { updateColorsFrom(colors) }
    val rippleIndication = rememberRipple()
    CompositionLocalProvider(
        LocalColors provides rememberedColors,
        LocalContentAlpha provides ContentAlpha.high,
        LocalContentColor provides colors.onBackground,
        LocalIndication provides rippleIndication,
        LocalRippleTheme provides CapitalRippleTheme,
        LocalShapes provides shapes,
        LocalTypography provides typography
    ) {
        ProvideTextStyle(value = typography.regular, content = content)
    }
}

@Immutable
private object CapitalRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = RippleTheme.defaultRippleColor(
        contentColor = LocalContentColor.current,
        lightTheme = CapitalTheme.colors.isLight
    )

    @Composable
    override fun rippleAlpha() = RippleTheme.defaultRippleAlpha(
        contentColor = LocalContentColor.current,
        lightTheme = CapitalTheme.colors.isLight
    )
}
