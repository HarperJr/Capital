package com.harper.core.component

import androidx.compose.foundation.Image
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import com.harper.core.theme.CapitalTheme

@Composable
fun CIcon(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    color: Color = CapitalTheme.colors.onBackground,
    onClick: () -> Unit = {}
) {
    IconButton(modifier = modifier, onClick = onClick) {
        Image(
            imageVector = imageVector,
            contentDescription = null,
            alignment = Alignment.Center,
            colorFilter = ColorFilter.tint(color = color)
        )
    }
}
