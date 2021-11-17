package com.harper.capital.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

private val primaryLight = Color(0xFFFFFFFF)
private val secondaryLight = Color(0xFFFFFFFF)
private val backgroundLight = Color(0xFFFFFFFF)
private val onPrimaryLight = Color(0xFFFFFFFF)
private val onSecondaryLight = Color(0xFFFFFFFF)
private val onBackgroundLight = Color(0xFFFFFFFF)
private val errorLight = Color(0xFFFFFFFF)

private val primaryDark = Color(0xFFFFFFFF)
private val secondaryDark = Color(0xFFFFFFFF)
private val backgroundDark = Color(0xFFFFFFFF)
private val onPrimaryDark = Color(0xFFFFFFFF)
private val onSecondaryDark = Color(0xFFFFFFFF)
private val onBackgroundDark = Color(0xFFFFFFFF)
private val errorDark = Color(0xFFFFFFFF)


@Stable
class CapitalColors(
    primary: Color,
    secondary: Color,
    background: Color,
    error: Color,
    onPrimary: Color,
    onSecondary: Color,
    onBackground: Color,
    isLight: Boolean
) {
    var primary by mutableStateOf(primary, structuralEqualityPolicy())
        internal set
    var secondary by mutableStateOf(secondary, structuralEqualityPolicy())
        internal set
    var background by mutableStateOf(background, structuralEqualityPolicy())
        internal set
    var error by mutableStateOf(error, structuralEqualityPolicy())
        internal set
    var onPrimary by mutableStateOf(onPrimary, structuralEqualityPolicy())
        internal set
    var onSecondary by mutableStateOf(onSecondary, structuralEqualityPolicy())
        internal set
    var onBackground by mutableStateOf(onBackground, structuralEqualityPolicy())
        internal set
    var isLight by mutableStateOf(isLight, structuralEqualityPolicy())
        internal set

    /**
     * Returns a copy of this Colors, optionally overriding some of the values.
     */
    fun copy(
        primary: Color = this.primary,
        secondary: Color = this.secondary,
        background: Color = this.background,
        error: Color = this.error,
        onPrimary: Color = this.onPrimary,
        onSecondary: Color = this.onSecondary,
        onBackground: Color = this.onBackground,
        isLight: Boolean = this.isLight
    ): CapitalColors = CapitalColors(
        primary,
        secondary,
        background,
        error,
        onPrimary,
        onSecondary,
        onBackground,
        isLight
    )

    override fun toString(): String {
        return "Colors(" +
                "primary=$primary, " +
                "secondary=$secondary, " +
                "background=$background, " +
                "error=$error, " +
                "onPrimary=$onPrimary, " +
                "onSecondary=$onSecondary, " +
                "onBackground=$onBackground, " +
                "isLight=$isLight" +
                ")"
    }
}

fun lightColors(
    primary: Color = primaryLight,
    secondary: Color = secondaryLight,
    background: Color = backgroundLight,
    error: Color = errorLight,
    onPrimary: Color = onPrimaryLight,
    onSecondary: Color = onSecondaryLight,
    onBackground: Color = onBackgroundLight
): CapitalColors = CapitalColors(
    primaryLight,
    secondaryLight,
    backgroundLight,
    error,
    onPrimaryLight,
    onSecondaryLight,
    onBackgroundLight,
    isLight = true
)

fun darkColors(
    primary: Color = primaryDark,
    secondary: Color = secondaryDark,
    background: Color = backgroundDark,
    error: Color = errorDark,
    onPrimary: Color = onPrimaryDark,
    onSecondary: Color = onSecondaryDark,
    onBackground: Color = onBackgroundDark
): CapitalColors = CapitalColors(
    primary,
    secondary,
    background,
    error,
    onPrimary,
    onSecondary,
    onBackground,
    isLight = false
)

fun CapitalColors.updateColorsFrom(other: CapitalColors) {
    primary = other.primary
    secondary = other.secondary
    background = other.background
    error = other.error
    onPrimary = other.onPrimary
    onSecondary = other.onSecondary
    onBackground = other.onBackground
    isLight = other.isLight
}

val LocalColors = staticCompositionLocalOf { lightColors() }