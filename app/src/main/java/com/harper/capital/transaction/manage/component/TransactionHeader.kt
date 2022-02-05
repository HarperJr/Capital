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
import com.harper.capital.component.AccountIconRound
import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.AccountColor
import com.harper.capital.domain.model.AccountIcon
import com.harper.capital.domain.model.AccountType
import com.harper.capital.domain.model.Currency
import com.harper.core.component.CPreview
import com.harper.core.theme.CapitalTheme

@Composable
fun TransactionHeader(modifier: Modifier = Modifier, source: Account, receiver: Account) {
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Box(contentAlignment = Alignment.CenterStart) {
            AccountIconRound(
                modifier = Modifier.padding(start = CapitalTheme.dimensions.imageMedium * 0.75f),
                color = receiver.color,
                icon = receiver.icon
            )
            AccountIconRound(
                modifier = Modifier
                    .background(color = CapitalTheme.colors.background, shape = CircleShape)
                    .padding(1.dp),
                color = source.color,
                icon = source.icon
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = CapitalTheme.dimensions.medium)
        ) {
            Text(
                text = source.name,
                style = CapitalTheme.typography.regular,
                color = CapitalTheme.colors.textPrimary
            )
            Text(
                text = receiver.name,
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
            source = Account(
                id = 0L,
                name = "Tinkoff",
                type = AccountType.ASSET,
                balance = 1000.0,
                currency = Currency.RUB,
                color = AccountColor.TINKOFF,
                icon = AccountIcon.TINKOFF,
                metadata = null
            ),
            receiver = Account(
                id = 0L,
                name = "Products",
                type = AccountType.LIABILITY,
                balance = 1000.0,
                currency = Currency.RUB,
                color = AccountColor.CATEGORY,
                icon = AccountIcon.PRODUCTS,
                metadata = null
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
            source = Account(
                id = 0L,
                name = "Tinkoff",
                type = AccountType.ASSET,
                balance = 1000.0,
                currency = Currency.RUB,
                color = AccountColor.TINKOFF,
                icon = AccountIcon.TINKOFF,
                metadata = null
            ),
            receiver = Account(
                id = 0L,
                name = "Products",
                type = AccountType.LIABILITY,
                balance = 1000.0,
                currency = Currency.RUB,
                color = AccountColor.CATEGORY,
                icon = AccountIcon.PRODUCTS,
                metadata = null
            )
        )
    }
}
