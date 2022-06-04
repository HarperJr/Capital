package com.harper.capital.ext

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import com.harper.capital.domain.model.AccountColor
import com.harper.core.theme.CapitalColors

val tinkoff: Color = Color(0xFF000000)
val tinkoffPlatinum: Color = Color(0xFFB7B7B7)
val sber: Color = Color(0xFF4DB45E)
val alpha: Color = Color(0xFFEC5B5B)
val vtbOld: Color = Color(0xFF244480)
val vtb: Color = Color(0xFF46B4CC)
val raiffeizen: Color = Color(0xFFFDF41F)
val liability: Color = Color(0xFF7F7F7F)
val debt: Color = Color(0xFF3587DF)

val onTinkoff: Color = Color(0xFF000000)
val onTinkoffPlatinum: Color = Color(0xFF797979)
val onSber: Color = Color(0xFF33853D)
val onAlpha: Color = Color(0xFFCC4646)
val onVtbOld: Color = Color(0xFF122955)
val onVtb: Color = Color(0xFF248196)
val onRaiffeizen: Color = Color(0xFFF2A634)
val onLiability: Color = Color(0xFF535353)
val onDebt: Color = Color(0xFF1469B4)

fun accountGradientBackgroundColor(color: AccountColor): Brush = when (color) {
    AccountColor.TINKOFF -> Brush.horizontalGradient(listOf(Color(0xFF000000), Color(0xFF242424)))
    AccountColor.TINKOFF_PLATINUM -> Brush.horizontalGradient(listOf(Color(0xFF6B6B6B), Color(0xFFB7B7B7)))
    AccountColor.SBER -> Brush.horizontalGradient(listOf(Color(0xFF4DB45E), Color(0xFF87B44D)))
    AccountColor.ALPHA -> Brush.horizontalGradient(listOf(Color(0xFFCC4646), Color(0xFFEC5B5B)))
    AccountColor.VTB_OLD -> Brush.horizontalGradient(listOf(Color(0xFF284180), Color(0xFF4F77D5)))
    AccountColor.VTB -> Brush.horizontalGradient(listOf(Color(0xFF248196), Color(0xFF46B4CC)))
    AccountColor.RAIFFEIZEN -> Brush.horizontalGradient(listOf(Color(0xFFF2A634), Color(0xFFFDF41F)))
    else -> Brush.horizontalGradient(listOf(Color(0xFFFFFFFF), Color(0xFFFFFFFF)))
}

fun accountBackgroundColor(color: AccountColor) = when (color) {
    AccountColor.TINKOFF -> tinkoff
    AccountColor.TINKOFF_PLATINUM -> tinkoffPlatinum
    AccountColor.SBER -> sber
    AccountColor.ALPHA -> alpha
    AccountColor.VTB_OLD -> vtbOld
    AccountColor.VTB -> vtb
    AccountColor.RAIFFEIZEN -> raiffeizen
    AccountColor.LIABILITY -> liability
    AccountColor.DEBT -> debt
}

fun accountOnBackgroundColorFor(color: Color) = when (color) {
    tinkoff -> onTinkoff
    tinkoffPlatinum -> onTinkoffPlatinum
    sber -> onSber
    alpha -> onAlpha
    vtbOld -> onVtbOld
    vtb -> onVtb
    raiffeizen -> onRaiffeizen
    liability -> onLiability
    debt -> onDebt
    else -> Color.Unspecified
}

fun accountContentColorFor(color: Color): Color = when (color) {
    tinkoff -> CapitalColors.White
    tinkoffPlatinum -> CapitalColors.White
    sber -> CapitalColors.White
    alpha -> CapitalColors.White
    vtbOld -> CapitalColors.White
    vtb -> CapitalColors.White
    raiffeizen -> CapitalColors.Black
    liability -> CapitalColors.White
    debt -> CapitalColors.White
    else -> Color.Unspecified
}
