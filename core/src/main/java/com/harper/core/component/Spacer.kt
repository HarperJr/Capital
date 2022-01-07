package com.harper.core.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun HorizontalSpacer(height: Dp) {
    Spacer(modifier = Modifier.height(height = height).fillMaxWidth())
}

@Composable
fun VerticalSpacer(width: Dp) {
    Spacer(modifier = Modifier.width(width = width).fillMaxHeight())
}