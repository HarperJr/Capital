package com.harper.capital.ext

import androidx.compose.ui.graphics.Color
import com.harper.capital.domain.model.AssetColor
import com.harper.core.theme.CapitalColors

val tinkoff: Color = Color(0xFF242424)
val tinkoffPlatinum: Color = Color(0xFFB7B7B7)
val sber: Color = Color(0xFF48B356)
val alpha: Color = Color(0xFFEE3124)
val vtbOld: Color = Color(0xFF244480)
val vtb: Color = Color(0xFF1ABAEF)
val raiffeizen: Color = Color(0xFFFDF41F)

val onTinkoff: Color = Color(0xFF141414)
val onTinkoffPlatinum: Color = Color(0xFF797979)
val onSber: Color = Color(0xFF33853D)
val onAlpha: Color = Color(0xFFA31F16)
val onVtbOld: Color = Color(0xFF122955)
val onVtb: Color = Color(0xFF117EA1)
val onRaiffeizen: Color = Color(0xFFAFA800)

fun assetBackgroundColor(color: AssetColor) = when(color) {
    AssetColor.TINKOFF -> tinkoff
    AssetColor.TINKOFF_PLATINUM -> tinkoffPlatinum
    AssetColor.SBER -> sber
    AssetColor.ALPHA -> alpha
    AssetColor.VTB_OLD -> vtbOld
    AssetColor.VTB -> vtb
    AssetColor.RAIFFEIZEN -> raiffeizen
}

fun assetOnBackgroundColorFor(color: Color) = when(color) {
    tinkoff -> onTinkoff
    tinkoffPlatinum -> onTinkoffPlatinum
    sber -> onSber
    alpha -> onAlpha
    vtbOld -> onVtbOld
    vtb -> onVtb
    raiffeizen -> onRaiffeizen
    else -> Color.Unspecified
}

fun assetContentColorFor(color: Color): Color = when(color) {
    tinkoff -> CapitalColors.White
    tinkoffPlatinum -> CapitalColors.White
    sber -> CapitalColors.White
    alpha -> CapitalColors.White
    vtbOld -> CapitalColors.White
    vtb -> CapitalColors.White
    raiffeizen -> CapitalColors.Black
    else -> Color.Unspecified
}
