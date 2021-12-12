package com.harper.capital.asset.component

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
import com.harper.capital.domain.model.AssetColor
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalTheme

@Composable
fun ColorSelectableCircle(
    color: AssetColor,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Box {
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
                    .background(color = Color(color.value), shape = CircleShape)
                    .clickable { onSelect.invoke() }
            )
        }
    }
}
