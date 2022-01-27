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
import com.harper.capital.component.AssetIconRound
import com.harper.capital.domain.model.Asset
import com.harper.capital.domain.model.AssetColor
import com.harper.capital.domain.model.AssetIcon
import com.harper.capital.domain.model.AssetMetadata
import com.harper.capital.domain.model.Currency
import com.harper.capital.domain.model.Transaction
import com.harper.capital.domain.model.TransactionType
import com.harper.core.component.CPreview
import com.harper.core.component.CVerticalSpacer
import com.harper.core.ext.formatWithCurrencySymbol
import com.harper.core.theme.CapitalTheme
import java.time.LocalDateTime

@Composable
fun TransactionItem(modifier: Modifier = Modifier, transaction: Transaction) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = CapitalTheme.dimensions.side,
                vertical = CapitalTheme.dimensions.small
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(contentAlignment = Alignment.CenterStart) {
            AssetIconRound(
                modifier = Modifier.padding(start = CapitalTheme.dimensions.imageMedium * 0.75f),
                color = transaction.assetTo.color,
                icon = transaction.assetTo.icon
            )
            AssetIconRound(
                modifier = Modifier
                    .background(color = CapitalTheme.colors.background, shape = CircleShape)
                    .padding(1.dp),
                color = transaction.assetFrom.color,
                icon = transaction.assetFrom.icon
            )
        }
        CVerticalSpacer(width = CapitalTheme.dimensions.medium)
        Column(modifier = Modifier.weight(1f)) {
            Text(text = transaction.assetFrom.name)
            Text(
                text = transaction.assetTo.name,
                style = CapitalTheme.typography.regularSmall,
                color = CapitalTheme.colors.textSecondary
            )
        }
        Text(
            text = transaction.amount.formatWithCurrencySymbol(transaction.currency.name),
            style = CapitalTheme.typography.buttonSmall
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionItemLight() {
    val assetFrom = Asset(
        id = 0L,
        name = "Tinkoff Black",
        amount = 1000.0,
        currency = Currency.RUB,
        color = AssetColor.TINKOFF,
        icon = AssetIcon.TINKOFF,
        metadata = AssetMetadata.Debet
    )
    val assetTo = Asset(
        id = 1L,
        name = "Products",
        amount = 100.0,
        currency = Currency.RUB,
        color = AssetColor.CATEGORY,
        icon = AssetIcon.PRODUCTS,
        metadata = AssetMetadata.Expense
    )
    val transaction = Transaction(
        id = 0L,
        type = TransactionType.EXPENSE,
        assetFrom = assetFrom,
        assetTo = assetTo,
        amount = 2400.0,
        currency = assetFrom.currency,
        dateTime = LocalDateTime.now(),
        comment = null,
        isScheduled = false,
        isIncluded = true
    )
    CPreview {
        TransactionItem(transaction = transaction)
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionItemDark() {
    val assetFrom = Asset(
        id = 0L,
        name = "Tinkoff Black",
        amount = 1000.0,
        currency = Currency.RUB,
        color = AssetColor.TINKOFF,
        icon = AssetIcon.TINKOFF,
        metadata = AssetMetadata.Debet
    )
    val assetTo = Asset(
        id = 1L,
        name = "Products",
        amount = 100.0,
        currency = Currency.RUB,
        color = AssetColor.CATEGORY,
        icon = AssetIcon.PRODUCTS,
        metadata = AssetMetadata.Expense
    )
    val transaction = Transaction(
        id = 0L,
        type = TransactionType.EXPENSE,
        assetFrom = assetFrom,
        assetTo = assetTo,
        amount = 2400.0,
        currency = assetFrom.currency,
        dateTime = LocalDateTime.now(),
        comment = null,
        isScheduled = false,
        isIncluded = true
    )
    CPreview(isDark = true) {
        TransactionItem(transaction = transaction)
    }
}
