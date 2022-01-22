package com.harper.core.component

import androidx.compose.material.Divider
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.harper.core.theme.CapitalTheme

@Composable
fun CSeparator(modifier: Modifier = Modifier) {
    Divider(
        modifier = modifier,
        color = CapitalTheme.colors.primaryVariant,
        thickness = 1.dp
    )
}