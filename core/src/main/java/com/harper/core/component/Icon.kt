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
fun Icon(modifier: Modifier = Modifier, @DrawableRes iconRes: Int, onClick: () -> Unit) {
    Icon(modifier, imageVector = ImageVector.vectorResource(id = iconRes), onClick)
}

@Composable
fun Icon(modifier: Modifier = Modifier, imageVector: ImageVector, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Image(
            modifier = modifier,
            imageVector = imageVector,
            contentDescription = null,
            alignment = Alignment.Center,
            colorFilter = ColorFilter.tint(color = CapitalTheme.colors.onBackground)
        )
    }
}
