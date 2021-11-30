package com.harper.core.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import com.harper.core.ext.compose.heightOrZero
import com.harper.core.ext.compose.widthOrZero
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme
import kotlin.math.max

@Composable
fun TextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    placeholder: String = "",
    leadingIcon: @Composable (() -> Unit)? = null,
    textStyle: TextStyle = CapitalTheme.typography.regular,
    textColor: Color = CapitalTheme.colors.onBackground,
    backgroundColor: Color = CapitalTheme.colors.secondary,
    hintColor: Color = CapitalColors.Boulder,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    singleLine: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onValueChange: (TextFieldValue) -> Unit
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
        singleLine = singleLine
    ) { innerTextField ->
        val text = value.text
        Box(
            modifier = Modifier
                .background(color = backgroundColor, shape = CapitalTheme.shapes.large)
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Layout(content = {
                if (text.isEmpty()) {
                    Text(
                        modifier = Modifier.layoutId("Placeholder"),
                        text = placeholder,
                        style = textStyle,
                        color = hintColor
                    )
                }
                if (leadingIcon != null) {
                    Box(
                        modifier = Modifier
                            .layoutId("LeadingIcon")
                            .defaultMinSize(24.dp, 24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CompositionLocalProvider(LocalContentColor provides hintColor) {
                            leadingIcon.invoke()
                        }
                    }
                }
                Box(modifier = Modifier.layoutId("TextField")) { innerTextField.invoke() }
            }, measurePolicy = { mesurables, constraints ->
                val leadingIconPlaceable = mesurables.find { it.layoutId == "LeadingIcon" }
                    ?.measure(constraints)
                val horizontalOffset = leadingIconPlaceable.widthOrZero()

                val textFieldPlaceable = mesurables.find { it.layoutId == "TextField" }
                    ?.measure(constraints.offset(horizontal = -horizontalOffset))

                val minWidth: Int
                val maxWidth: Int
                if (constraints.hasBoundedWidth) {
                    val width = (constraints.maxWidth)
                        .coerceIn(constraints.minWidth, constraints.maxWidth)
                    minWidth = width
                    maxWidth = width
                } else {
                    minWidth = textFieldPlaceable.widthOrZero()
                    maxWidth = textFieldPlaceable.widthOrZero()
                }

                val placeholderPlaceable = mesurables.find { it.layoutId == "Placeholder" }
                    ?.measure(
                        constraints.copy(minWidth = minWidth, maxWidth = maxWidth)
                            .offset(horizontal = -horizontalOffset)
                    )
                val width = leadingIconPlaceable.widthOrZero() + textFieldPlaceable.widthOrZero()
                val height = max(textFieldPlaceable.heightOrZero(), leadingIconPlaceable.heightOrZero())
                layout(width = width, height = height) {
                    val verticalOffset = (height - textFieldPlaceable.heightOrZero()) / 2
                    leadingIconPlaceable?.placeRelative(0, 0)
                    textFieldPlaceable?.placeRelative(horizontalOffset, verticalOffset)
                    placeholderPlaceable?.placeRelative(horizontalOffset, verticalOffset)
                }
            })
        }
    }
}

@Composable
fun TextField(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String = "",
    leadingIcon: @Composable (() -> Unit)? = null,
    textStyle: TextStyle = CapitalTheme.typography.regular,
    textColor: Color = CapitalTheme.colors.onBackground,
    backgroundColor: Color = CapitalTheme.colors.secondary,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    singleLine: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onValueChange: (String) -> Unit
) {
    var textFieldValueState by remember { mutableStateOf(TextFieldValue(text = value)) }
    val textFieldValue = textFieldValueState.copy(text = value)
    TextField(
        modifier = modifier,
        value = textFieldValue,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        textStyle = textStyle,
        textColor = textColor,
        backgroundColor = backgroundColor,
        visualTransformation = visualTransformation,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        interactionSource = interactionSource,
        onValueChange = {
            textFieldValueState = it
            if (value != it.text) {
                onValueChange(it.text)
            }
        }
    )
}

@Preview
@Composable
private fun TextFieldLight() {
    ComposablePreview {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(CapitalTheme.colors.background)
                .padding(16.dp)
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = "",
                placeholder = "Enter some text here",
                leadingIcon = {
                    Image(
                        modifier = Modifier.padding(end = 8.dp),
                        imageVector = CapitalIcons.Search,
                        contentDescription = null
                    )
                }
            ) {}
        }
    }
}

@Preview
@Composable
private fun TextFieldDark() {
    ComposablePreview(isDark = true) {
        Box(
            modifier = Modifier
                .background(CapitalTheme.colors.background)
                .padding(16.dp)
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = "TextField"
            ) {}
        }
    }
}

