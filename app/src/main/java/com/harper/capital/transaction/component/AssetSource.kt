package com.harper.capital.transaction.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harper.capital.domain.model.Asset
import com.harper.capital.domain.model.AssetColor
import com.harper.capital.domain.model.AssetIcon
import com.harper.capital.domain.model.AssetMetadata
import com.harper.capital.domain.model.Currency
import com.harper.capital.ext.getImageVector
import com.harper.core.component.ComposablePreview
import com.harper.core.ext.formatWithCurrencySymbol
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme

@Composable
fun AssetSource(
    modifier: Modifier = Modifier,
    asset: Asset,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Box(modifier = modifier) {
        val borderColor = if (isSelected) CapitalColors.Blue else CapitalTheme.colors.secondary
        Row(
            modifier = Modifier
                .background(
                    color = CapitalTheme.colors.background,
                    shape = CircleShape
                )
                .border(width = 1.dp, color = borderColor, shape = CircleShape)
                .clickable { onSelect.invoke() }
        ) {
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .size(36.dp)
                    .background(
                        color = Color(asset.color.value),
                        shape = CircleShape
                    )
            ) {
                Image(
                    modifier = Modifier.align(Alignment.Center),
                    imageVector = asset.icon.getImageVector(),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = CapitalColors.White)
                )
            }
            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 24.dp)
            ) {
                Text(
                    text = asset.name,
                    style = CapitalTheme.typography.regular,
                    color = CapitalTheme.colors.onBackground
                )
                Text(
                    text = asset.amount.formatWithCurrencySymbol(asset.currency.name),
                    style = CapitalTheme.typography.regularSmall,
                    color = CapitalTheme.colors.secondary
                )
            }
        }
        if (isSelected) {
            Image(
                modifier = Modifier.align(Alignment.TopEnd),
                imageVector = CapitalIcons.RoundCheck,
                contentDescription = null
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AssetSourceLight() {
    ComposablePreview {
        AssetSource(
            modifier = Modifier.padding(16.dp),
            asset = Asset(
                id = 0L,
                name = "Tinkoff Black",
                amount = 1500.0,
                currency = Currency.RUB,
                color = AssetColor.DARK_TINKOFF,
                icon = AssetIcon.TINKOFF,
                metadata = AssetMetadata.Default
            ),
            isSelected = false
        ) {}
    }
}

@Preview(showBackground = true)
@Composable
private fun AssetSourceDark() {
    ComposablePreview(isDark = true) {
        AssetSource(
            modifier = Modifier.padding(16.dp),
            asset = Asset(
                id = 0L,
                name = "Tinkoff Black",
                amount = 1500.0,
                currency = Currency.RUB,
                color = AssetColor.DARK_TINKOFF,
                icon = AssetIcon.TINKOFF,
                metadata = AssetMetadata.Default
            ),
            isSelected = true
        ) {}
    }
}
