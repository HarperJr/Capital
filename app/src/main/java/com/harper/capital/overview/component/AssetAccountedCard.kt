package com.harper.capital.overview.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harper.capital.R
import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.Currency
import com.harper.core.component.AmountText
import com.harper.core.component.ComposablePreview
import com.harper.core.ext.compose.assetCardSize
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalTheme

@Composable
fun AssetAccountedCard(
    modifier: Modifier = Modifier,
    account: Account
) {
    Card(
        modifier = modifier.assetCardSize(),
        backgroundColor = CapitalColors.Thunder,
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
            AmountText(
                modifier = Modifier
                    .align(Alignment.Center),
                amount = account.amount,
                currencyIso = account.currency.name,
                color = CapitalColors.White,
                style = CapitalTheme.typography.header
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
