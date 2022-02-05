package com.harper.capital.history.comonent

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.harper.capital.component.AccountIconRound
import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.AccountColor
import com.harper.capital.domain.model.AccountIcon
import com.harper.capital.domain.model.AccountType
import com.harper.capital.domain.model.ChargeTransaction
import com.harper.capital.domain.model.Currency
import com.harper.core.component.CPreview
import com.harper.core.component.CVerticalSpacer
import com.harper.core.ext.formatWithCurrencySymbol
import com.harper.core.theme.CapitalTheme
import java.time.LocalDateTime

@Composable
fun ChargeTransactionItem(modifier: Modifier = Modifier, transaction: ChargeTransaction) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = CapitalTheme.dimensions.side,
                vertical = CapitalTheme.dimensions.medium
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(contentAlignment = Alignment.CenterStart) {
            AccountIconRound(
                modifier = Modifier,
                color = transaction.receiver.color,
                icon = transaction.receiver.icon
            )
        }
        CVerticalSpacer(width = CapitalTheme.dimensions.side)
        Text(modifier = Modifier.weight(1f), text = transaction.receiver.name)
        Text(
            text = transaction.amount.formatWithCurrencySymbol(transaction.receiver.currency.name),
            style = CapitalTheme.typography.buttonSmall
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ChargeTransactionItemLight() {
    val receiver = Account(
        id = 1L,
        name = "Products",
        type = AccountType.LIABILITY,
        balance = 100.0,
        currency = Currency.RUB,
        color = AccountColor.CATEGORY,
        icon = AccountIcon.PRODUCTS,
        metadata = null
    )
    val transaction = ChargeTransaction(
        id = 0L,
        receiver = receiver,
        amount = 2400.0,
        dateTime = LocalDateTime.now(),
        comment = null,
        isScheduled = false
    )
    CPreview {
        ChargeTransactionItem(transaction = transaction)
    }
}

@Preview(showBackground = true)
@Composable
fun ChargeTransactionItemDark() {
    val receiver = Account(
        id = 1L,
        name = "Products",
        type = AccountType.ASSET,
        balance = 100.0,
        currency = Currency.RUB,
        color = AccountColor.TINKOFF,
        icon = AccountIcon.TINKOFF,
        metadata = null
    )
    val transaction = ChargeTransaction(
        id = 0L,
        receiver = receiver,
        amount = 2400.0,
        dateTime = LocalDateTime.now(),
        comment = null,
        isScheduled = false
    )
    CPreview(isDark = true) {
        ChargeTransactionItem(transaction = transaction)
    }
}
