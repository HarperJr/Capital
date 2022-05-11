package com.harper.capital.main.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.harper.capital.component.AccountRoundIconsRow
import com.harper.capital.domain.model.TransferTransaction
import com.harper.core.component.CVerticalSpacer
import com.harper.core.theme.CapitalTheme

@Composable
fun FavoriteTransferTransactionItem(modifier: Modifier = Modifier, transaction: TransferTransaction, onClick: () -> Unit) {
    Row(modifier = modifier
        .clickable { onClick.invoke() }
        .padding(horizontal = CapitalTheme.dimensions.side, vertical = CapitalTheme.dimensions.medium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AccountRoundIconsRow(accounts = listOf(transaction.source, transaction.receiver))
        CVerticalSpacer(width = CapitalTheme.dimensions.medium)
        Column {
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
