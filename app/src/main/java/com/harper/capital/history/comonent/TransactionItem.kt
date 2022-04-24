package com.harper.capital.history.comonent

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harper.capital.component.AccountIconRound
import com.harper.capital.domain.model.*
import com.harper.core.component.CPreview
import com.harper.core.component.CVerticalSpacer
import com.harper.core.ext.formatWithCurrencySymbol
import com.harper.core.theme.CapitalTheme
import java.time.LocalDateTime

@Composable
fun TransactionItem(modifier: Modifier = Modifier, transaction: Transaction, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(
                horizontal = CapitalTheme.dimensions.side,
                vertical = CapitalTheme.dimensions.medium
            )
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(contentAlignment = Alignment.CenterStart) {
                transaction.ledgers.forEachIndexed { index, ledger ->
                    AccountIconRound(
                        modifier = Modifier.padding(start = CapitalTheme.dimensions.imageMedium * index * 0.75f),
                        color = ledger.account.color,
                        icon = ledger.account.icon
                    )
                }
            }
            CVerticalSpacer(width = CapitalTheme.dimensions.side)
            Column(modifier = Modifier.weight(1f)) {
                transaction.ledgers.forEachIndexed { index, ledger ->
                    val isFirst = index == 0
                    if (isFirst) {
                        Text(text = ledger.account.name)
                    } else {
                        Text(
                            text = ledger.account.name,
                            style = CapitalTheme.typography.regularSmall,
                            color = CapitalTheme.colors.textSecondary
                        )
                    }
                }
            }
            Text(
                text = transaction.source.amount.formatWithCurrencySymbol(transaction.source.account.currency.name),
                style = CapitalTheme.typography.buttonSmall
            )
        }
        transaction.comment?.let { comment ->
            Box(
                modifier = Modifier
                    .align(Alignment.End)
                    .background(
                        color = CapitalTheme.colors.primaryVariant,
                        shape = CapitalTheme.shapes.large.copy(topStart = CornerSize(0.dp))
                    )
                    .padding(
                        horizontal = CapitalTheme.dimensions.medium,
                        vertical = CapitalTheme.dimensions.small
                    )
            ) {
                Text(text = comment)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TransferTransactionItemLight() {
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
    val transaction = Transaction(
        id = 0L,
        ledgers = listOf(
            Ledger(0L, source, LedgerType.CREDIT, -1050.0),
            Ledger(0L, receiver, LedgerType.DEBIT, 1050.0)
        ),
        dateTime = LocalDateTime.now(),
        comment = "Bought some food",
        isScheduled = false
    )
    CPreview {
        TransactionItem(transaction = transaction) {}
    }
}

@Preview(showBackground = true)
@Composable
fun TransferTransactionItemDark() {
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
    val transaction = Transaction(
        id = 0L,
        ledgers = listOf(
            Ledger(0L, source, LedgerType.CREDIT, -1050.0),
            Ledger(0L, receiver, LedgerType.DEBIT, 1050.0)
        ),
        dateTime = LocalDateTime.now(),
        comment = null,
        isScheduled = false
    )
    CPreview(isDark = true) {
        TransactionItem(transaction = transaction) {}
    }
}
