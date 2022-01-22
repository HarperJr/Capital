package com.harper.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalTextStyle
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
import com.harper.core.theme.CapitalTheme

@Composable
fun CAmountText(
    modifier: Modifier = Modifier,
    amount: Double,
    currencyIso: String,
    style: TextStyle = LocalTextStyle.current,
    color: Color = LocalContentColor.current
) {
    Text(
        modifier = modifier,
        text = buildAnnotatedString {
            val text = amount.formatWithCurrencySymbol(currencyIso)
            val commaIndex = text.indexOf(',')
            append(text)
            if (commaIndex != -1) {
                addStyle(SpanStyle(color = color.copy(alpha = 0.35f)), start = commaIndex + 1, end = text.length - 1)
            }
        },
        color = color,
        style = style
    )
}

@Preview
@Composable
private fun CAmountTextLight() {
    CPreview {
        Box(
            modifier = Modifier
                .background(CapitalTheme.colors.background)
                .padding(16.dp)
        ) {
            CAmountText(
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
private fun CAmountTextDark() {
    CPreview(isDark = true) {
        Box(
            modifier = Modifier
                .background(CapitalTheme.colors.background)
                .padding(16.dp)
        ) {
            CAmountText(
                amount = 1455244.42,
                currencyIso = "RUB",
                style = CapitalTheme.typography.regular,
                color = CapitalTheme.colors.onBackground
            )
        }
    }
}
