package com.harper.core.component

import android.icu.text.NumberFormat
import android.icu.util.ULocale
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.tooling.preview.Preview
import com.harper.core.ext.formatAmount
import com.harper.core.ext.formatCurrencySymbol
import com.harper.core.theme.CapitalTheme
import kotlin.math.min

@Composable
fun CAmountTextField(
    modifier: Modifier = Modifier,
    amount: Double,
    currencyIso: String? = null,
    placeholder: String? = null,
    title: @Composable (() -> Unit)? = null,
    error: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    textStyle: TextStyle = LocalTextStyle.current,
    textColor: Color = LocalContentColor.current,
    backgroundColor: Color = CapitalTheme.colors.primaryVariant,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions { },
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onValueChange: (Double) -> Unit
) {
    val amountTextValue = rememberSaveable(amount) { mutableStateOf(amount.format()) }
    CTextField(
        modifier = modifier.onFocusChanged { focusState ->
            val value = amountTextValue.value
            if (!focusState.isFocused && value.endsWithDecimalSeparator()) {
                amountTextValue.value = amountTextValue.value.removeDecimalSeparator()
            }
        },
        value = amountTextValue.value,
        placeholder = placeholder,
        title = title,
        error = error,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        onValueChange = { value ->
            if (value.endsWithDecimalSeparator() && !amountTextValue.value.endsWithDecimalSeparator()) {
                amountTextValue.value = value
            } else {
                if (value.isBlank()) {
                    onValueChange.invoke(0.0)
                } else {
                    val newValue = value.parse()
                    if (newValue != amount) {
                        onValueChange.invoke(newValue)
                    }
                }
            }
        },
        textStyle = textStyle,
        textColor = textColor,
        backgroundColor = backgroundColor,
        keyboardOptions = keyboardOptions.copy(keyboardType = KeyboardType.Number),
        cursorColor = textColor,
        singleLine = true,
        visualTransformation = { annotatedString ->
            val newValue = annotatedString.text
            val transformedText = if (newValue.endsWithDecimalSeparator()) {
                newValue.removeDecimalSeparator()
                    .parse()
                    .formatAmount(minFractionDigits = 0)
                    .plus(",")
            } else {
                newValue.parse()
                    .formatAmount(minFractionDigits = 0)
            }
            val offsetDiff = transformedText.length - annotatedString.length
            val transformedTextWithCurrency = AnnotatedString(
                text = transformedText.plus(currencyIso?.formatCurrencySymbol()?.let { " $it" }.orEmpty())
            )
            TransformedText(
                text = transformedTextWithCurrency,
                offsetMapping = object : OffsetMapping {

                    override fun originalToTransformed(offset: Int): Int =
                        min(offset + offsetDiff, transformedText.length)

                    override fun transformedToOriginal(offset: Int): Int =
                        min(offset - offsetDiff, annotatedString.length)
                }
            )
        },
        interactionSource = interactionSource,
        keyboardActions = keyboardActions
    )
}

private fun String.endsWithDecimalSeparator(): Boolean =
    this.endsWith(suffix = ".", ignoreCase = true) ||
        this.endsWith(suffix = ",", ignoreCase = true)

private fun String.removeDecimalSeparator(): String =
    this.removeSuffix(suffix = ".")

private fun Double.format(): String = NumberFormat.getInstance(ULocale.ENGLISH).apply {
    isGroupingUsed = false
    minimumFractionDigits = 0
    maximumFractionDigits = 2
}.format(this)

private fun String.parse(): Double = NumberFormat.getInstance(ULocale.ENGLISH).apply {
    isGroupingUsed = false
    minimumFractionDigits = 0
    maximumFractionDigits = 2
}.parse(this).toDouble()


@Preview(showBackground = true)
@Composable
private fun AmountTextFieldLight() {
    CPreview {
        CAmountTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(CapitalTheme.dimensions.side),
            amount = 1000000.5,
            currencyIso = "RUB",
            textStyle = CapitalTheme.typography.regular,
            textColor = CapitalTheme.colors.secondary
        ) {}
    }
}

@Preview(showBackground = true)
@Composable
private fun AmountTextFieldDark() {
    CPreview(isDark = true) {
        CAmountTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(CapitalTheme.dimensions.side),
            amount = 1221.54,
            currencyIso = "RUB",
            textStyle = CapitalTheme.typography.regular,
            textColor = CapitalTheme.colors.secondary
        ) {}
    }
}
