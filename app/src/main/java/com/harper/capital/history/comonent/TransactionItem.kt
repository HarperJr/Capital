package com.harper.capital.history.comonent

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
import com.harper.capital.domain.model.TransferTransaction
import com.harper.core.component.CPreview
import com.harper.core.component.CVerticalSpacer
import com.harper.core.ext.formatWithCurrencySymbol
import com.harper.core.theme.CapitalTheme
import java.time.LocalDateTime

@Composable
fun TransactionItem(modifier: Modifier = Modifier, transaction: TransferTransaction) {
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
                modifier = Modifier.padding(start = CapitalTheme.dimensions.imageMedium * 0.75f),
                color = transaction.receiver.color,
                icon = transaction.receiver.icon
            )
            AccountIconRound(
                modifier = Modifier
                    .background(color = CapitalTheme.colors.background, shape = CircleShape)
                    .padding(1.dp),
                color = transaction.source.color,
                icon = transaction.source.icon
            )
        }
        CVerticalSpacer(width = CapitalTheme.dimensions.side)
        Column(modifier = Modifier.weight(1f)) {
            Text(text = transaction.source.name)
            Text(
                text = transaction.receiver.name,
                style = CapitalTheme.typography.regularSmall,
                color = CapitalTheme.colors.textSecondary
            )
        }
        Text(
            text = transaction.amount.formatWithCurrencySymbol(transaction.source.currency.name),
            style = CapitalTheme.typography.buttonSmall
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionItemLight() {
    val source = Account(
        id = 0L,
        name = "Tinkoff Black",
        type = AccountType.ASSET,
        balance = 1000.0,
        currency = Currency.RUB,
        color = AccountColor.TINKOFF,
        icon = AccountIcon.TINKOFF,
        metadata = null
    )
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
    val transaction = TransferTransaction(
        id = 0L,
        source = source,
        receiver = receiver,
        amount = 2400.0,
        dateTime = LocalDateTime.now(),
        comment = null,
        isScheduled = false
    )
    CPreview {
        TransactionItem(transaction = transaction)
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionItemDark() {
    val source = Account(
        id = 0L,
        name = "Tinkoff Black",
        type = AccountType.ASSET,
        balance = 1000.0,
        currency = Currency.RUB,
        color = AccountColor.TINKOFF,
        icon = AccountIcon.TINKOFF,
        metadata = null
    )
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
    val transaction = TransferTransaction(
        id = 0L,
        source = source,
        receiver = receiver,
        amount = 2400.0,
        dateTime = LocalDateTime.now(),
        comment = null,
        isScheduled = false
    )
    CPreview(isDark = true) {
        TransactionItem(transaction = transaction)
    }
}
