package com.harper.capital.overview.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.Currency
import com.harper.core.component.AmountText
import com.harper.core.component.ComposablePreview
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalTheme

@Composable
fun AssetAccountedCard(
    modifier: Modifier = Modifier,
    account: Account
) {
    Card(
        modifier = modifier.size(width = 264.dp, height = 160.dp),
        backgroundColor = CapitalColors.Thunder,
        elevation = 4.dp,
        shape = CapitalTheme.shapes.extraLarge
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AmountText(
                modifier = Modifier
                    .align(Alignment.Center),
                amount = account.amount,
                currencyIso = account.currency.name,
                color = CapitalColors.White,
                style = CapitalTheme.typography.regular
            )
        }
    }
}

@Preview
@Composable
private fun AssetAccountedCardLight() {
    ComposablePreview {
        Box(
            modifier = Modifier
                .background(CapitalTheme.colors.background)
                .padding(16.dp)
        ) {
            AssetAccountedCard(account = Account(45000.00, Currency.RUB))
        }
    }
}

@Preview
@Composable
private fun AssetAccountedCardDark() {
    ComposablePreview(isDark = true) {
        Box(
            modifier = Modifier
                .background(CapitalTheme.colors.background)
                .padding(16.dp)
        ) {
            AssetAccountedCard(account = Account(75000.00, Currency.EUR))
        }
    }
}
