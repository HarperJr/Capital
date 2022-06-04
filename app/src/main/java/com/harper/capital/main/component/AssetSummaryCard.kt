package com.harper.capital.main.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.harper.capital.R
import com.harper.capital.domain.model.AccountColor
import com.harper.capital.domain.model.Currency
import com.harper.capital.ext.accountBackgroundColor
import com.harper.capital.ext.accountContentColorFor
import com.harper.capital.ext.accountGradientBackgroundColor
import com.harper.capital.main.domain.model.Summary
import com.harper.core.component.CAmountText
import com.harper.core.component.CPreview
import com.harper.core.ext.compose.assetCardSize
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalTheme

@Composable
fun AssetSummaryCard(
    modifier: Modifier = Modifier,
    summary: Summary
) {
    val cardBackgroundColor = accountBackgroundColor(AccountColor.VTB_OLD)
    Card(
        modifier = modifier.assetCardSize(),
        backgroundColor = CapitalColors.Transparent,
        contentColor = accountContentColorFor(cardBackgroundColor),
        elevation = 0.dp,
        shape = CapitalTheme.shapes.extraLarge
    ) {
        Box(modifier = Modifier.background(accountGradientBackgroundColor(AccountColor.VTB_OLD))) {
            Image(
                modifier = Modifier.fillMaxSize(),
                imageVector = ImageVector.vectorResource(id = R.drawable.bg_card_whiteness),
                contentDescription = null,
                alignment = Alignment.CenterEnd
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = CapitalTheme.dimensions.side, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(CapitalTheme.dimensions.medium)
            ) {
                Text(text = stringResource(id = R.string.balance), style = CapitalTheme.typography.regular)
                CAmountText(
                    amount = summary.balance,
                    currencyIso = summary.currency.name,
                    style = CapitalTheme.typography.header.copy(fontSize = 32.sp),
                )
            }
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
