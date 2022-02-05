package com.harper.capital.history.comonent

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.harper.capital.domain.model.Currency
import com.harper.core.component.CPreview
import com.harper.core.ext.formatWithCurrencySymbol
import com.harper.core.theme.CapitalTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val MMMMDDDateFormatter = DateTimeFormatter.ofPattern("MMMM d")

@Composable
fun TransactionDateScopeItem(
    modifier: Modifier = Modifier,
    date: LocalDate,
    amount: Double,
    currency: Currency
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = CapitalTheme.dimensions.side,
                vertical = CapitalTheme.dimensions.side
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = date.format(MMMMDDDateFormatter),
            textAlign = TextAlign.Start,
            style = CapitalTheme.typography.subtitle
        )
        Text(
            modifier = Modifier.weight(1f),
            text = amount.formatWithCurrencySymbol(currency.name),
            textAlign = TextAlign.End,
            style = CapitalTheme.typography.titleSmall,
            color = CapitalTheme.colors.secondary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionDateScopeItemLight() {
    CPreview {
        TransactionDateScopeItem(date = LocalDate.now(), amount = 2000.0, currency = Currency.RUB)
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionDateScopeItemDark() {
    CPreview(isDark = true) {
        TransactionDateScopeItem(date = LocalDate.now(), amount = 2000.0, currency = Currency.RUB)
    }
}
