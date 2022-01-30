package com.harper.capital.main.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.harper.capital.domain.model.Currency
import com.harper.capital.ext.assetBackgroundColor
import com.harper.capital.ext.assetContentColorFor
import com.harper.capital.main.domain.model.Summary
import com.harper.core.component.CAmountText
import com.harper.core.component.CPreview
import com.harper.core.ext.compose.assetCardSize
import com.harper.core.theme.CapitalTheme

@Composable
fun AssetSummaryCard(
    modifier: Modifier = Modifier,
    summary: Summary
) {
    val cardBackgroundColor = assetBackgroundColor(AssetColor.TINKOFF_PLATINUM)
    Card(
        modifier = modifier.assetCardSize(),
        backgroundColor = cardBackgroundColor,
        contentColor = assetContentColorFor(cardBackgroundColor),
        elevation = 6.dp,
        shape = CapitalTheme.shapes.extraLarge
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize(0.5f),
            imageVector = ImageVector.vectorResource(id = R.drawable.bg_card_whiteness),
            contentDescription = null,
            alignment = Alignment.CenterEnd
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            CAmountText(
                modifier = Modifier
                    .align(Alignment.Center),
                amount = summary.balance,
                currencyIso = summary.currency.name,
                style = CapitalTheme.typography.header
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AssetAccountedCardLight() {
    CPreview {
        AssetSummaryCard(
            modifier = Modifier.padding(16.dp),
            summary = Summary(expenses = 12000.0, balance = 10000.0, Currency.EUR)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AssetAccountedCardDark() {
    CPreview(isDark = true) {
        AssetSummaryCard(
            modifier = Modifier.padding(16.dp),
            summary = Summary(expenses = 12000.0, balance = 10000.0, Currency.EUR)
        )
    }
}
