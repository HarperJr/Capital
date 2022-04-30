package com.harper.capital.transaction.manage.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.harper.capital.component.AccountRoundIconsRow
import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.AccountColor
import com.harper.capital.domain.model.AccountIcon
import com.harper.capital.domain.model.AccountType
import com.harper.capital.domain.model.Currency
import com.harper.capital.domain.model.TransferTransaction
import com.harper.core.component.CPreview
import com.harper.core.theme.CapitalTheme
import java.time.LocalDateTime

@Composable
fun TransactionHeader(modifier: Modifier = Modifier, transaction: TransferTransaction) {
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        AccountRoundIconsRow(accounts = listOf(transaction.source, transaction.receiver))
        Column(
            modifier = Modifier
                .padding(horizontal = CapitalTheme.dimensions.medium)
                .weight(1f)
        ) {
            Text(
                text = transaction.source.name,
                style = CapitalTheme.typography.regular,
                color = CapitalTheme.colors.textPrimary
            )
            Text(
                text = transaction.receiver.name,
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
        TransferHeaderPreview()
    }
}

@Preview(showBackground = true)
@Composable
private fun TransactionHeaderDark() {
    CPreview(isDark = true) {
        TransferHeaderPreview()
    }
}

@Composable
private fun TransferHeaderPreview() {
    TransactionHeader(
        modifier = Modifier.padding(CapitalTheme.dimensions.side),
        transaction = TransferTransaction(
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
            ),
            sourceAmount = 1000.0,
            receiverAmount = 1000.0,
            dateTime = LocalDateTime.of(2022, 4, 25, 10, 20, 20),
            comment = null,
            isScheduled = false
        )
    )
}
