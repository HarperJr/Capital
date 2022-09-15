package com.harper.core.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme
import com.harper.core.theme.capitalButtonColors
import com.harper.core.theme.capitalButtonElevation

private val minButtonHeight = 42.dp

private val defaultButtonColors: ButtonColors
    @Composable
    get() = capitalButtonColors()

private val secondaryButtonColors: ButtonColors
    @Composable
    get() = capitalButtonColors(
        backgroundColor = CapitalTheme.colors.primaryVariant,
        disabledBackgroundColor = CapitalTheme.colors.primaryVariant,
        contentColor = CapitalTheme.colors.secondary,
        disabledContentColor = CapitalTheme.colors.secondaryVariant
    )

@Composable
private fun CButtonBase(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    elevation: Dp = 0.dp,
    buttonColors: ButtonColors = defaultButtonColors,
    icon: @Composable (() -> Unit)? = null,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier.height(minButtonHeight),
        shape = CapitalTheme.shapes.large,
        colors = buttonColors,
        elevation = capitalButtonElevation(elevation),
        enabled = enabled,
        onClick = onClick
    ) {
        if (icon != null) {
            Box(
                modifier = Modifier
                    .padding(end = ButtonDefaults.IconSpacing)
                    .size(ButtonDefaults.IconSize)
            ) {
                icon.invoke()
            }
        }
        Text(
            text = text,
            style = CapitalTheme.typography.button,
            color = LocalContentColor.current,
            maxLines = 1
        )
    }
}

@Composable
fun CButtonPrimary(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: @Composable (() -> Unit)? = null,
    onClick: () -> Unit
) {
    CButtonBase(
        modifier = modifier,
        text = text,
        enabled = enabled,
        buttonColors = defaultButtonColors,
        icon = icon,
        onClick = onClick
    )
}

@Composable
fun CButtonSecondary(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: @Composable (() -> Unit)? = null,
    onClick: () -> Unit
) {
    CButtonBase(
        modifier = modifier,
        text = text,
        enabled = enabled,
        buttonColors = secondaryButtonColors,
        icon = icon,
        onClick = onClick
    )
}

@Composable
fun CButtonCommon(
    text: String,
    modifier: Modifier = Modifier,
    elevation: Dp = 0.dp,
    enabled: Boolean = true,
    backgroundColor: Color = Color.White,
    contentColor: Color = LocalContentColor.current,
    icon: @Composable (() -> Unit)? = null,
    onClick: () -> Unit
) {
    CButtonBase(
        modifier = modifier,
        text = text,
        enabled = enabled,
        elevation = elevation,
        buttonColors = capitalButtonColors(
            backgroundColor = backgroundColor,
            disabledBackgroundColor = backgroundColor,
            contentColor = contentColor,
            disabledContentColor = contentColor
        ),
        icon = icon,
        onClick = onClick
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CButtonOverlap(modifier: Modifier = Modifier, enabled: Boolean = true, content: @Composable (Modifier) -> Unit) {
    val isImeVisible = WindowInsets.isImeVisible
    if (isImeVisible) {
        content.invoke(modifier.transformOverlap(enabled))
    } else {
        content.invoke(modifier)
    }
}

@OptIn(ExperimentalLayoutApi::class)
internal fun Modifier.transformOverlap(enabled: Boolean = true): Modifier = composed(inspectorInfo = debugInspectorInfo {
    name = "transformOverlapModifier"
    value = true
}) {
    val isImeVisible = WindowInsets.isImeVisible
    val color = CapitalTheme.colors.secondary
    if (isImeVisible && enabled) {
        this.then(
            Modifier
                .wrapContentWidth()
                .shadow(
                    elevation = CapitalTheme.dimensions.small,
                    shape = CapitalTheme.shapes.extraLarge,
                    ambientColor = color,
                    spotColor = color
                )
                .padding(CapitalTheme.dimensions.small)
        )
    } else {
        this
    }
}

@Preview(showBackground = true)
@Composable
private fun CButtonLight() {
    CPreview {
        CButtonPreview()
    }
}

@Preview(showBackground = true)
@Composable
private fun CButtonDark() {
    CPreview(isDark = true) {
        CButtonPreview()
    }
}

@Composable
private fun CButtonPreview() {
    Column(
        modifier = Modifier.padding(CapitalTheme.dimensions.side),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(CapitalTheme.dimensions.side)
    ) {
        CButtonPrimary(modifier = Modifier.fillMaxWidth(), text = "Push me") {}
        CButtonPrimary(modifier = Modifier.fillMaxWidth(), enabled = false, text = "Push me") {}
        CButtonSecondary(modifier = Modifier.fillMaxWidth(), text = "Push me") {}
        CButtonSecondary(modifier = Modifier.fillMaxWidth(), enabled = false, text = "Push me") {}
        CButtonCommon(modifier = Modifier.fillMaxWidth(), text = "Push me") {}
        CButtonPrimary(
            modifier = Modifier.fillMaxWidth(),
            text = "Push me, i have a long text",
            icon = {
                Icon(
                    imageVector = CapitalIcons.Add,
                    contentDescription = null
                )
            }
        ) {}
    }
}
