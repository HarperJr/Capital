package com.harper.capital.transaction.manage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harper.capital.component.AssetIconRound
import com.harper.capital.domain.model.Asset
import com.harper.capital.domain.model.AssetColor
import com.harper.capital.domain.model.AssetIcon
import com.harper.capital.domain.model.AssetMetadata
import com.harper.capital.domain.model.Currency
import com.harper.core.component.CPreview
import com.harper.core.theme.CapitalTheme

@Composable
fun TransactionHeader(modifier: Modifier = Modifier, assetFrom: Asset, assetTo: Asset) {
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Box(contentAlignment = Alignment.CenterStart) {
            AssetIconRound(
                modifier = Modifier.padding(start = CapitalTheme.dimensions.imageMedium * 0.75f),
                color = assetTo.color,
                icon = assetTo.icon
            )
            AssetIconRound(
                modifier = Modifier
                    .background(color = CapitalTheme.colors.background, shape = CircleShape)
                    .padding(1.dp),
                color = assetFrom.color,
                icon = assetFrom.icon
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = CapitalTheme.dimensions.medium)
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

@Preview(showBackground = true)
@Composable
private fun TransactionHeaderLight() {
    CPreview {
        TransactionHeader(
            modifier = Modifier.padding(CapitalTheme.dimensions.side),
            assetFrom = Asset(
                id = 0L,
                name = "Tinkoff",
                balance = 1000.0,
                currency = Currency.RUB,
                color = AssetColor.TINKOFF,
                icon = AssetIcon.TINKOFF,
                metadata = AssetMetadata.Debet
            ),
            assetTo = Asset(
                id = 0L,
                name = "Products",
                balance = 1000.0,
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
private fun TransactionHeaderDark() {
    CPreview(isDark = true) {
        TransactionHeader(
            modifier = Modifier.padding(CapitalTheme.dimensions.side),
            assetFrom = Asset(
                id = 0L,
                name = "Tinkoff",
                balance = 1000.0,
                currency = Currency.RUB,
                color = AssetColor.TINKOFF,
                icon = AssetIcon.TINKOFF,
                metadata = AssetMetadata.Debet
            ),
            assetTo = Asset(
                id = 0L,
                name = "Products",
                balance = 1000.0,
                currency = Currency.RUB,
                color = AssetColor.CATEGORY,
                icon = AssetIcon.PRODUCTS,
                metadata = AssetMetadata.Expense
            )
        )
    }
}
