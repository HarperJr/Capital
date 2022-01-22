package com.harper.core.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.graphics.Color

private val primaryLight = CapitalColors.White
private val primaryVariantLight = CapitalColors.GreyLight
private val secondaryLight = CapitalColors.Blue
private val secondaryVariantLight = CapitalColors.BlueLight
private val textPrimaryLight = CapitalColors.Black
private val textSecondaryLight = CapitalColors.GreyDark
private val backgroundLight = CapitalColors.White
private val onPrimaryLight = CapitalColors.Black
private val onSecondaryLight = CapitalColors.White
private val onBackgroundLight = CapitalColors.Black
private val errorLight = CapitalColors.RedError

private val primaryDark = CapitalColors.Black
private val primaryVariantDark = CapitalColors.GreyDarkest
private val secondaryDark = CapitalColors.Blue
private val secondaryVariantDark = CapitalColors.BlueLight
private val textPrimaryDark = CapitalColors.White
private val textSecondaryDark = CapitalColors.GreyDark
private val backgroundDark = CapitalColors.Black
private val onPrimaryDark = CapitalColors.White
private val onSecondaryDark = CapitalColors.White
private val onBackgroundDark = CapitalColors.White
private val errorDark = CapitalColors.RedError

@Stable
class CapitalColors(
    primary: Color,
    primaryVariant: Color,
    secondary: Color,
    secondaryVariant: Color,
    textPrimary: Color,
    textSecondary: Color,
    background: Color,
    error: Color,
    onPrimary: Color,
    onSecondary: Color,
    onBackground: Color,
    isLight: Boolean
) {
    var primary by mutableStateOf(primary, structuralEqualityPolicy())
        internal set
    var primaryVariant by mutableStateOf(primaryVariant, structuralEqualityPolicy())
        internal set
    var secondary by mutableStateOf(secondary, structuralEqualityPolicy())
        internal set
    var secondaryVariant by mutableStateOf(secondaryVariant, structuralEqualityPolicy())
        internal set
    var textPrimary by mutableStateOf(textPrimary, structuralEqualityPolicy())
        internal set
    var textSecondary by mutableStateOf(textSecondary, structuralEqualityPolicy())
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
        primaryVariant: Color = this.primaryVariant,
        secondary: Color = this.secondary,
        secondaryVariant: Color = this.secondaryVariant,
        textPrimary: Color = this.textPrimary,
        textSecondary: Color = this.textSecondary,
        background: Color = this.background,
        error: Color = this.error,
        onPrimary: Color = this.onPrimary,
        onSecondary: Color = this.onSecondary,
        onBackground: Color = this.onBackground,
        isLight: Boolean = this.isLight
    ): CapitalColors = CapitalColors(
        primary,
        primaryVariant,
        secondary,
        secondaryVariant,
        textPrimary,
        textSecondary,
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
                "primaryVariant=$primaryVariant, " +
                "secondary=$secondary, " +
                "secondaryVariant=$secondaryVariant, " +
                "textPrimary=$textPrimary, " +
                "textSecondary=$textSecondary, " +
                "background=$background, " +
                "error=$error, " +
                "onPrimary=$onPrimary, " +
                "onSecondary=$onSecondary, " +
                "onBackground=$onBackground, " +
                "isLight=$isLight" +
                ")"
    }

    companion object {
        val White: Color = Color.White
        val Black: Color = Color.Black
        val Transparent: Color = Color.Transparent
        val GreyLight: Color = Color(0xFFE5E5E5)
        val GreyMedium: Color = Color(0xFFCCCCCC)
        val GreyDark: Color = Color(0xFF7F7F7F)
        val GreyDarkest: Color = Color(0xFF242424)
        val Blue: Color = Color(0xFF2186EB)
        val BlueLight: Color = Color(0xFF8DB3FF)
        val RedError: Color = Color(0xFFFF4848)
    }
}

fun lightColors(
    primary: Color = primaryLight,
    primaryVariant: Color = primaryVariantLight,
    secondary: Color = secondaryLight,
    secondaryVariant: Color = secondaryVariantLight,
    textPrimary: Color = textPrimaryLight,
    textSecondary: Color = textSecondaryLight,
    background: Color = backgroundLight,
    error: Color = errorLight,
    onPrimary: Color = onPrimaryLight,
    onSecondary: Color = onSecondaryLight,
    onBackground: Color = onBackgroundLight
): CapitalColors = CapitalColors(
    primary,
    primaryVariant,
    secondary,
    secondaryVariant,
    textPrimary,
    textSecondary,
    background,
    error,
    onPrimary,
    onSecondary,
    onBackground,
    isLight = true
)

fun darkColors(
    primary: Color = primaryDark,
    primaryVariant: Color = primaryVariantDark,
    secondary: Color = secondaryDark,
    accent: Color = secondaryVariantDark,
    textPrimary: Color = textPrimaryDark,
    textSecondary: Color = textSecondaryDark,
    background: Color = backgroundDark,
    error: Color = errorDark,
    onPrimary: Color = onPrimaryDark,
    onSecondary: Color = onSecondaryDark,
    onBackground: Color = onBackgroundDark
): CapitalColors = CapitalColors(
    primary,
    primaryVariant,
    secondary,
    accent,
    textPrimary,
    textSecondary,
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