package com.harper.capital.transaction.manage.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.harper.capital.component.AccountIconRound
import com.harper.capital.domain.model.*
import com.harper.core.component.CPreview
import com.harper.core.theme.CapitalTheme

@Composable
fun TransactionHeader(modifier: Modifier = Modifier, accounts: List<Account>) {
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Box(contentAlignment = Alignment.CenterStart) {
            accounts.forEachIndexed { index, account ->
                AccountIconRound(
                    modifier = Modifier.padding(start = CapitalTheme.dimensions.imageMedium * index * 0.75f),
                    color = account.color,
                    icon = account.icon
                )
            }
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = CapitalTheme.dimensions.medium)
        ) {
            accounts.forEachIndexed { index, account ->
                val isFirst = index == 0
                if (isFirst) {
                    Text(
                        text = account.name,
                        style = CapitalTheme.typography.regular,
                        color = CapitalTheme.colors.textPrimary
                    )
                } else {
                    Text(
                        text = account.name,
                        style = CapitalTheme.typography.regularSmall,
                        color = CapitalTheme.colors.textSecondary
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TransactionHeaderLight() {
    CPreview {
        TransactionHeader(
            modifier = Modifier.padding(CapitalTheme.dimensions.side),
            accounts = listOf(
                Account(
                    id = 0L,
                    name = "Tinkoff",
                    type = AccountType.ASSET,
                    balance = 1000.0,
                    currency = Currency.RUB,
                    color = AccountColor.TINKOFF,
                    icon = AccountIcon.TINKOFF,
                    metadata = null
                ),
                Account(
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
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TransactionHeaderDark() {
    CPreview(isDark = true) {
        TransactionHeader(
            modifier = Modifier.padding(CapitalTheme.dimensions.side),
            accounts = listOf(
                Account(
                    id = 0L,
                    name = "Tinkoff",
                    type = AccountType.ASSET,
                    balance = 1000.0,
                    currency = Currency.RUB,
                    color = AccountColor.TINKOFF,
                    icon = AccountIcon.TINKOFF,
                    metadata = null
                ),
                Account(
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
        )
    }
}
