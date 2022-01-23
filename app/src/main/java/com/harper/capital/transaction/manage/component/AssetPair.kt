package com.harper.capital.transaction.manage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harper.capital.domain.model.Asset
import com.harper.capital.domain.model.AssetColor
import com.harper.capital.domain.model.AssetIcon
import com.harper.capital.domain.model.AssetMetadata
import com.harper.capital.domain.model.Currency
import com.harper.capital.ext.assetBackgroundColor
import com.harper.capital.ext.assetContentColorFor
import com.harper.capital.ext.getImageVector
import com.harper.core.component.CPreview
import com.harper.core.theme.CapitalTheme

@Composable
fun AssetPair(modifier: Modifier = Modifier, assetFrom: Asset, assetTo: Asset) {
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Box {
            AssetIcon(
                modifier = Modifier.padding(start = CapitalTheme.dimensions.imageMedium * 0.75f),
                asset = assetTo
            )
            AssetIcon(
                modifier = Modifier.border(
                    width = 1.dp,
                    color = CapitalTheme.colors.background,
                    shape = CircleShape
                ),
                asset = assetFrom
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = CapitalTheme.dimensions.small)
        ) {
            Text(
                text = assetFrom.name,
                style = CapitalTheme.typography.regular,
                color = CapitalTheme.colors.textPrimary
            )
            Text(
                text = assetTo.name,
                style = CapitalTheme.typography.regularSmall,
                color = CapitalTheme.colors.textSecondary
            )
        }
    }
}

@Composable
private fun AssetIcon(modifier: Modifier = Modifier, asset: Asset) {
    val circleColor = assetBackgroundColor(asset.color)
    Box(
        modifier = modifier
            .size(CapitalTheme.dimensions.imageMedium)
            .background(
                color = circleColor,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = asset.icon.getImageVector(),
            contentDescription = null,
            tint = assetContentColorFor(circleColor)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AssetPairLight() {
    CPreview {
        AssetPair(
            modifier = Modifier.padding(CapitalTheme.dimensions.side),
            assetFrom = Asset(
                id = 0L,
                name = "Tinkoff",
                amount = 1000.0,
                currency = Currency.RUB,
                color = AssetColor.TINKOFF,
                icon = AssetIcon.TINKOFF,
                metadata = AssetMetadata.Debet
            ),
            assetTo = Asset(
                id = 0L,
                name = "Products",
                amount = 1000.0,
                currency = Currency.RUB,
                color = AssetColor.CATEGORY,
                icon = AssetIcon.PRODUCTS,
                metadata = AssetMetadata.Expense
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AssetPairDark() {
    CPreview(isDark = true) {
        AssetPair(
            modifier = Modifier.padding(CapitalTheme.dimensions.side),
            assetFrom = Asset(
                id = 0L,
                name = "Tinkoff",
                amount = 1000.0,
                currency = Currency.RUB,
                color = AssetColor.TINKOFF,
                icon = AssetIcon.TINKOFF,
                metadata = AssetMetadata.Debet
            ),
            assetTo = Asset(
                id = 0L,
                name = "Products",
                amount = 1000.0,
                currency = Currency.RUB,
                color = AssetColor.CATEGORY,
                icon = AssetIcon.PRODUCTS,
                metadata = AssetMetadata.Expense
            )
        )
    }
}
