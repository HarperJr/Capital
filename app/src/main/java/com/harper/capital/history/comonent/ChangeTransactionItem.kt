package com.harper.capital.history.comonent

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.harper.capital.R
import com.harper.capital.component.AccountIconRound
import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.AccountColor
import com.harper.capital.domain.model.AccountIcon
import com.harper.capital.domain.model.AccountType
import com.harper.capital.domain.model.ChangeTransaction
import com.harper.capital.domain.model.Currency
import com.harper.core.component.CPreview
import com.harper.core.component.CVerticalSpacer
import com.harper.core.ext.formatWithCurrencySymbol
import com.harper.core.theme.CapitalTheme
import java.time.LocalDateTime

@Composable
fun ChangeTransactionItem(modifier: Modifier = Modifier, transaction: ChangeTransaction) {
    Row(
        modifier = modifier
            .padding(
                horizontal = CapitalTheme.dimensions.side,
                vertical = CapitalTheme.dimensions.medium
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AccountIconRound(
            color = transaction.account.color,
            icon = transaction.account.icon
        )
        CVerticalSpacer(width = CapitalTheme.dimensions.side)
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(
                id = R.string.transaction_change,
                transaction.amount.formatWithCurrencySymbol(transaction.account.currency.name)
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ChangeTransactionItemLight() {
    CPreview(isDark = false) {
        ChangeTransactionItemPreview()
    }
}

@Preview(showBackground = true)
@Composable
private fun ChangeTransactionItemDark() {
    CPreview(isDark = true) {
        ChangeTransactionItemPreview()
    }
}

@Composable
private fun ChangeTransactionItemPreview() {
    val account = Account(
        id = 0L,
        name = "Tinkoff Black",
        type = AccountType.ASSET,
        balance = 1000.0,
        currency = Currency.RUB,
        color = AccountColor.TINKOFF,
        icon = AccountIcon.TINKOFF,
        metadata = null
    )
    val transaction = ChangeTransaction(
        account = account,
        amount = 1000.0,
        dateTime = LocalDateTime.of(2022, 4, 26, 20, 10, 10),
        comment = null,
        isScheduled = false
    )
    ChangeTransactionItem(transaction = transaction)
}
