package com.harper.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harper.core.ext.formatWithCurrencySymbol
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalTheme

@Composable
fun AmountText(modifier: Modifier = Modifier, amount: Double, currencyIso: String, style: TextStyle, color: Color) {
    Text(
        modifier = modifier,
        text = buildAnnotatedString {
            val text = amount.formatWithCurrencySymbol(currencyIso)
            val commaIndex = text.indexOf(',')
            append(text)
            if (commaIndex != -1) {
                addStyle(SpanStyle(color = CapitalColors.GreyMedium), start = commaIndex + 1, end = text.length - 1)
            }
        },
        color = color,
        style = style
    )
}

@Preview
@Composable
private fun AmountTextLight() {
    ComposablePreview {
        Box(
            modifier = Modifier
                .background(CapitalTheme.colors.background)
                .padding(16.dp)
        ) {
            AmountText(
                amount = 1455244.42,
                currencyIso = "USD",
                style = CapitalTheme.typography.regular,
                color = CapitalTheme.colors.onBackground
            )
        }
    }
}

@Preview
@Composable
private fun AmountTextDark() {
    ComposablePreview(isDark = true) {
        Box(
            modifier = Modifier
                .background(CapitalTheme.colors.background)
                .padding(16.dp)
        ) {
            AmountText(
                amount = 1455244.42,
                currencyIso = "RUB",
                style = CapitalTheme.typography.regular,
                color = CapitalTheme.colors.onBackground
            )
        }
    }
}
