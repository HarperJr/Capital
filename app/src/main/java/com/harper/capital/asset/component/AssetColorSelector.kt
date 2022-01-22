package com.harper.capital.asset.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harper.capital.R
import com.harper.capital.domain.model.AssetColor
import com.harper.capital.ext.assetBackgroundColor
import com.harper.core.component.CPreview
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalTheme

@Composable
fun AssetColorSelector(
    color: AssetColor,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    val selectorColor = if (isSelected) CapitalColors.GreyLight else CapitalColors.Transparent
    Box {
        Box(
            modifier = Modifier
                .background(color = selectorColor, shape = RoundedCornerShape(6.dp))
                .padding(4.dp)
        ) {
            Card(
                modifier = Modifier
                    .size(width = 46.dp, height = 28.dp)
                    .clickable { onSelect.invoke() },
                backgroundColor = assetBackgroundColor(color)
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxSize(0.5f),
                    imageVector = ImageVector.vectorResource(id = R.drawable.bg_card_whiteness),
                    contentDescription = null,
                    alignment = Alignment.CenterEnd
                )
            }
        }
    }
}

@Preview
@Composable
fun AssetColorSelectorLight() {
    CPreview {
        Box(
            modifier = Modifier
                .background(color = CapitalTheme.colors.background)
                .padding(16.dp)
        ) {
            AssetColorSelector(
                color = AssetColor.TINKOFF,
                isSelected = false,
                {}
            )
        }
    }
}

@Preview
@Composable
fun AssetColorSelectorDark() {
    CPreview(isDark = true) {
        Box(
            modifier = Modifier
                .background(color = CapitalTheme.colors.background)
                .padding(16.dp)
        ) {
            AssetColorSelector(
                color = AssetColor.TINKOFF,
                isSelected = false,
                {}
            )
        }
    }
}
