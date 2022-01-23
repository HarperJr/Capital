package com.harper.core.component

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun CIcon(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    color: Color = LocalContentColor.current,
    onClick: () -> Unit = {}
) {
    IconButton(modifier = modifier, onClick = onClick) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            tint = color
        )
    }
}
