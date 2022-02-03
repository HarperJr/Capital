package com.harper.core.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme

private val minTextFieldHeight = 32.dp

@Composable
private fun CBasicTextField(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String?,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    textStyle: TextStyle,
    textColor: Color,
    backgroundColor: Color,
    placeholderColor: Color,
    cursorColor: Color,
    visualTransformation: VisualTransformation,
    keyboardActions: KeyboardActions,
    keyboardOptions: KeyboardOptions,
    singleLine: Boolean,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onValueChange: (String) -> Unit
) {
    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = { onValueChange.invoke(it) },
        textStyle = textStyle.copy(color = textColor),
        visualTransformation = visualTransformation,
        interactionSource = interactionSource,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        singleLine = singleLine,
        cursorBrush = SolidColor(value = cursorColor),
    ) { innerTextField ->
        Row(
            modifier = Modifier
                .background(color = backgroundColor, shape = CapitalTheme.shapes.large)
                .defaultMinSize(minHeight = minTextFieldHeight)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (leadingIcon != null) {
                Box(
                    modifier = Modifier
                        .defaultMinSize(24.dp, 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CompositionLocalProvider(LocalContentColor provides placeholderColor) {
                        leadingIcon.invoke()
                    }
                }
            }
            Box(modifier = Modifier.weight(1f, fill = trailingIcon != null)) {
                if (value.isEmpty()) {
                    Text(
                        text = placeholder.orEmpty(),
                        style = textStyle,
                        color = placeholderColor
                    )
                }
                innerTextField.invoke()
            }
            if (trailingIcon != null) {
                Box(
                    modifier = Modifier.defaultMinSize(24.dp, 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CompositionLocalProvider(LocalContentColor provides placeholderColor) {
                        trailingIcon.invoke()
                    }
                }
            }
        }
    }
}

@Composable
fun CTextField(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String? = null,
    title: @Composable (() -> Unit)? = null,
    error: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    textStyle: TextStyle = LocalTextStyle.current,
    textColor: Color = LocalContentColor.current,
    placeholderColor: Color = CapitalTheme.colors.textSecondary,
    backgroundColor: Color = CapitalTheme.colors.primaryVariant,
    cursorColor: Color = LocalContentColor.current,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    singleLine: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onValueChange: (String) -> Unit
) {
    Column(modifier = modifier) {
        if (title != null) {
            title.invoke()
            CHorizontalSpacer(height = CapitalTheme.dimensions.small)
        }
        CBasicTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            placeholder = placeholder,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            textStyle = textStyle,
            textColor = textColor,
            placeholderColor = placeholderColor,
            backgroundColor = backgroundColor,
            cursorColor = cursorColor,
            visualTransformation = visualTransformation,
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardOptions,
            singleLine = singleLine,
            interactionSource = interactionSource,
            onValueChange = {
                if (value != it) {
                    onValueChange(it)
                }
            }
        )
        error?.invoke()
    }
}

@Preview
@Composable
private fun CapitalTextFieldLight() {
    CPreview {
        CTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(CapitalTheme.dimensions.side),
            value = "",
            placeholder = "Enter some text here",
            leadingIcon = {
                Image(
                    modifier = Modifier.padding(end = 8.dp),
                    imageVector = CapitalIcons.Search,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = LocalContentColor.current)
                )
            }
        ) {}
    }
}

@Preview
@Composable
private fun CapitalTextFieldDark() {
    CPreview(isDark = true) {
        CTextField(
            modifier = Modifier.padding(CapitalTheme.dimensions.side),
            value = "TextField"
        ) {}
    }
}

