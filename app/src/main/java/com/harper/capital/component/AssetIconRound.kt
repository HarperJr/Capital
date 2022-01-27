package com.harper.capital.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.harper.capital.domain.model.AssetColor
import com.harper.capital.domain.model.AssetIcon
import com.harper.capital.ext.assetBackgroundColor
import com.harper.capital.ext.assetContentColorFor
import com.harper.capital.ext.getImageVector
import com.harper.core.component.CPreview
import com.harper.core.theme.CapitalTheme

@Composable
fun AssetIconRound(modifier: Modifier = Modifier, color: AssetColor, icon: AssetIcon) {
    val backgroundColor = assetBackgroundColor(color)
    CompositionLocalProvider(LocalContentColor provides assetContentColorFor(backgroundColor)) {
        Box(
            modifier = modifier
                .size(CapitalTheme.dimensions.imageMedium)
                .background(color = backgroundColor, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon.getImageVector(),
                contentDescription = null
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AssetIconRoundLight() {
    CPreview {
        AssetIconRound(
            modifier = Modifier.padding(CapitalTheme.dimensions.side),
            color = AssetColor.TINKOFF,
            icon = AssetIcon.TINKOFF
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AssetIconRoundDark() {
    CPreview(isDark = true) {
        AssetIconRound(
            modifier = Modifier.padding(CapitalTheme.dimensions.side),
            color = AssetColor.TINKOFF,
            icon = AssetIcon.TINKOFF
        )
    }
}
