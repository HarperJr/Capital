package com.harper.core.component

import android.icu.text.DecimalFormat
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harper.core.ext.formatAmount
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
        modifier = modifier,
        value = amountTextValue.value,
        placeholder = placeholder,
        title = title,
        error = error,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        onValueChange = { value ->
            val newValue = value.toDoubleOrNull()
            if (newValue != null && newValue != amount) {
                amountTextValue.value = value
                onValueChange.invoke(newValue)
            } else {
                amountTextValue.value = "0"
                onValueChange.invoke(0.0)
            }
        },
        textStyle = textStyle,
        textColor = textColor,
        backgroundColor = backgroundColor,
        keyboardOptions = keyboardOptions.copy(keyboardType = KeyboardType.Number),
        cursorColor = textColor,
        singleLine = true,
        visualTransformation = { annotatedString ->
            val newValue = annotatedString.text.toDoubleOrNull() ?: 0.0
            val transformedText = newValue.formatAmount(minFractionDigits = 0)
            val offsetDiff = transformedText.length - annotatedString.length
            val transformedTextWithCurrencyIfExists =
                AnnotatedString(text = newValue.formatAmount(currencyIso, minFractionDigits = 0))
            TransformedText(
                text = transformedTextWithCurrencyIfExists,
                offsetMapping = object : OffsetMapping {

                    override fun originalToTransformed(offset: Int): Int =
                        min(offset + offsetDiff, transformedText.length)

                    override fun transformedToOriginal(offset: Int): Int =
                        min(offset - offsetDiff, annotatedString.length)
                })
        },
        interactionSource = interactionSource,
        keyboardActions = keyboardActions
    )
}

private fun Double.format(
    maxFractionDigits: Int = 2
): String = DecimalFormat("#.##").apply {
    minimumFractionDigits = 0
    maximumFractionDigits = maxFractionDigits
}.format(this)

@Preview(showBackground = true)
@Composable
private fun AmountTextFieldLight() {
    CPreview {
        CAmountTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            amount = 1000000.0,
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
            modifier = Modifier.padding(16.dp),
            amount = 1221.0,
            currencyIso = "RUB",
            textStyle = CapitalTheme.typography.regular,
            textColor = CapitalTheme.colors.secondary
        ) {}
    }
}
