package com.harper.core.component

import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun CIcon(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    color: Color = LocalContentColor.current,
    isEnabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    IconButton(modifier = modifier, enabled = isEnabled, onClick = onClick) {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
            Icon(
                imageVector = imageVector,
                contentDescription = null,
                tint = color.copy(alpha = LocalContentAlpha.current)
            )
        }
    }
}
