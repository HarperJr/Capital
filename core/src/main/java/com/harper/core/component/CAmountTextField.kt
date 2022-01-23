package com.harper.core.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.harper.core.ext.formatWithoutZeroDecimal
import com.harper.core.theme.CapitalTheme

@Composable
fun CAmountTextField(
    modifier: Modifier = Modifier,
    amount: Double,
    placeholder: String = "",
    title: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    textStyle: TextStyle = LocalTextStyle.current,
    textColor: Color = LocalContentColor.current,
    backgroundColor: Color = CapitalTheme.colors.primaryVariant,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions { },
    onValueChange: (Double) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    CTextField(
        modifier = modifier,
        value = amount.formatWithoutZeroDecimal(),
        placeholder = placeholder,
        title = title,
        leadingIcon = leadingIcon,
        onValueChange = { value ->
            if (value.isEmpty()) {
                onValueChange.invoke(0.0)
            } else {
                onValueChange.invoke(value.toDouble())
            }
        },
        textStyle = textStyle,
        textColor = textColor,
        backgroundColor = backgroundColor,
        keyboardOptions = keyboardOptions.copy(keyboardType = KeyboardType.Number),
        singleLine = true,
        visualTransformation = { annotatedString ->
            val transformedText =
                AnnotatedString(text = annotatedString.text.toDouble().formatAmount())
            val offsetDiff = transformedText.length - annotatedString.length
            TransformedText(
                text = transformedText,
                offsetMapping = object : OffsetMapping {

                    override fun originalToTransformed(offset: Int): Int = offset + offsetDiff

                    override fun transformedToOriginal(offset: Int): Int = offset - offsetDiff
                })
        },
        interactionSource = interactionSource,
        keyboardActions = keyboardActions
    )
}

@Preview(showBackground = true)
@Composable
private fun AmountTextFieldLight() {
    CPreview {
        CAmountTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            amount = 1225.44,
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
            amount = 1225.0,
            textStyle = CapitalTheme.typography.regular,
            textColor = CapitalTheme.colors.secondary
        ) {}
    }
}
