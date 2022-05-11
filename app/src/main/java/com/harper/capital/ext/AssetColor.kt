package com.harper.capital.ext

import androidx.compose.ui.graphics.Color
import com.harper.capital.domain.model.AccountColor
import com.harper.core.theme.CapitalColors

val tinkoff: Color = Color(0xFF242424)
val tinkoffPlatinum: Color = Color(0xFFB7B7B7)
val sber: Color = Color(0xFF48B356)
val alpha: Color = Color(0xFFEE3124)
val vtbOld: Color = Color(0xFF244480)
val vtb: Color = Color(0xFF1ABAEF)
val raiffeizen: Color = Color(0xFFFDF41F)
val liability: Color = Color(0xFFE9E9E9)
val debt: Color = Color(0xFF3587DF)

val onTinkoff: Color = Color(0xFF141414)
val onTinkoffPlatinum: Color = Color(0xFF797979)
val onSber: Color = Color(0xFF33853D)
val onAlpha: Color = Color(0xFFA31F16)
val onVtbOld: Color = Color(0xFF122955)
val onVtb: Color = Color(0xFF117EA1)
val onRaiffeizen: Color = Color(0xFFAFA800)
val onLiability: Color = Color(0xFFA5A5A5)
val onDebt: Color = Color(0xFF1469B4)

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
    liability -> CapitalColors.Black
    debt -> CapitalColors.White
    else -> Color.Unspecified
}
