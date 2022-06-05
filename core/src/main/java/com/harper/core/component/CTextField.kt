package com.harper.core.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme
import kotlinx.coroutines.launch

private val minTextFieldHeight = 38.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CBasicTextField(
    value: String,
    placeholder: String?,
    singleLine: Boolean,
    textStyle: TextStyle,
    textColor: Color,
    backgroundColor: Color,
    placeholderColor: Color,
    cursorColor: Color,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onValueChange: (String) -> Unit
) {
    val relocation = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()
    BasicTextField(
        modifier = modifier
            .bringIntoViewRequester(bringIntoViewRequester = relocation)
            .onFocusChanged {
                if (it.isFocused) {
                    coroutineScope.launch { relocation.bringIntoView() }
                }
            },
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
                .background(color = backgroundColor, shape = CapitalTheme.shapes.extraLarge)
                .defaultMinSize(minHeight = minTextFieldHeight)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(CapitalTheme.dimensions.medium)
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
    value: String,
    modifier: Modifier = Modifier,
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
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(CapitalTheme.dimensions.medium)) {
        title?.invoke()
        CBasicTextField(
            value = value,
            placeholder = placeholder,
            singleLine = singleLine,
            textStyle = textStyle,
            textColor = textColor,
            backgroundColor = backgroundColor,
            placeholderColor = placeholderColor,
            cursorColor = cursorColor,
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            visualTransformation = visualTransformation,
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardOptions,
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
            leadingIcon = { Icon(CapitalIcons.Search, null) }
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

