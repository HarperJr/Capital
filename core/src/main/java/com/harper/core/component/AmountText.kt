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
import androidx.compose.ui.unit.sp
import com.harper.core.ext.formatCurrency
import com.harper.core.theme.CapitalTheme

@Composable
fun AmountText(modifier: Modifier = Modifier, text: String, style: TextStyle, color: Color) {
    Text(
        modifier = modifier,
        text = buildAnnotatedString {
            val commaIndex = text.indexOf(',')
            append(text)
            if (commaIndex != -1) {
                addStyle(SpanStyle(fontSize = 24.sp), start = 0, end = commaIndex + 1)
                addStyle(SpanStyle(fontSize = 14.sp), start = commaIndex + 1, end = text.length - 1)
                addStyle(
                    SpanStyle(fontSize = 24.sp),
                    start = text.length - 1,
                    end = text.length
                )
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
                text = 1455244.42.formatCurrency("USD"),
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
                text = 1455244.42.formatCurrency("RUB"),
                style = CapitalTheme.typography.regular,
                color = CapitalTheme.colors.onBackground
            )
        }
    }
}
