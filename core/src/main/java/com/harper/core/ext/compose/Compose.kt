package com.harper.core.ext.compose

import androidx.compose.ui.layout.Placeable

fun Placeable?.widthOrZero(): Int = this?.width ?: 0

fun Placeable?.heightOrZero(): Int = this?.height ?: 0
