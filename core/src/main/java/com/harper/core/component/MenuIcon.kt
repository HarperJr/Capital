package com.harper.core.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.harper.core.theme.CapitalTheme

@Composable
fun MenuIcon(modifier: Modifier = Modifier, @DrawableRes iconRes: Int, hasTint: Boolean = true, onClick: () -> Unit = {}) {
    MenuIcon(modifier, imageVector = ImageVector.vectorResource(id = iconRes), hasTint, onClick)
}

@Composable
fun MenuIcon(modifier: Modifier = Modifier, imageVector: ImageVector, hasTint: Boolean = true, onClick: () -> Unit = {}) {
    IconButton(modifier = modifier, onClick = onClick) {
        Image(
            imageVector = imageVector,
            contentDescription = null,
            alignment = Alignment.Center,
            colorFilter = ColorFilter.tint(color = CapitalTheme.colors.onBackground).takeIf { hasTint }
        )
    }
}
