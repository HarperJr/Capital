package com.harper.core.component

import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.harper.core.theme.CapitalColors

@Composable
fun Separator(modifier: Modifier = Modifier) {
    Divider(
        modifier = modifier,
        color = CapitalColors.Silver,
        thickness = 1.dp
    )
}