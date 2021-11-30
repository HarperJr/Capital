package com.harper.capital.overview.asset.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.harper.capital.spec.domain.AssetColor
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalTheme

@Composable
fun ColorSelectableCircle(
    color: AssetColor,
    isFirst: Boolean,
    isLast: Boolean,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Box(
        modifier = Modifier.padding(
            start = if (isFirst) 0.dp else 8.dp,
            end = if (isLast) 0.dp else 8.dp
        )
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .background(
                    color = if (isSelected) {
                        CapitalTheme.colors.secondary
                    } else {
                        CapitalColors.Transparent
                    }, shape = CircleShape
                )
                .clickable { onSelect.invoke() }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
                    .background(color = color.toComposableColor(), shape = CircleShape)
                    .clickable { onSelect.invoke() }
            )
        }
    }
}

private fun AssetColor.toComposableColor(): Color = when (this) {
    AssetColor.DARK -> CapitalColors.Thunder
    AssetColor.GREEN -> CapitalColors.Green
    AssetColor.RED -> CapitalColors.Red
    AssetColor.BLUE -> CapitalColors.DodgerBlue
    AssetColor.LIGHT_BLUE -> CapitalColors.CornflowerBlue
}