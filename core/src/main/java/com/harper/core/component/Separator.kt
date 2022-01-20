package com.harper.core.component

import androidx.compose.material.Divider
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Separator(modifier: Modifier = Modifier) {
    Divider(
        modifier = modifier,
        color = LocalContentColor.current.copy(alpha = 0.5f),
        thickness = 1.dp
    )
}