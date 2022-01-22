package com.harper.capital.asset.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.harper.capital.domain.model.AssetColor
import com.harper.capital.domain.model.AssetIcon
import com.harper.capital.ext.assetBackgroundColor
import com.harper.capital.ext.getImageVector
import com.harper.capital.ext.assetContentColorFor
import com.harper.capital.ext.assetOnBackgroundColorFor
import com.harper.core.component.CAmountTextField
import com.harper.core.component.CPreview
import com.harper.core.component.CTextField
import com.harper.core.ext.compose.assetCardSize
import com.harper.core.theme.CapitalTheme

@Composable
fun AssetEditableCard(
    modifier: Modifier = Modifier,
    name: String,
    amount: Double,
    icon: AssetIcon,
    color: AssetColor,
    onIconClick: () -> Unit,
    onAmountChange: (Double) -> Unit,
    onNameChange: (String) -> Unit
) {
    val cardBackgroundColor = assetBackgroundColor(color)
    Card(
        modifier = modifier.assetCardSize(),
        backgroundColor = cardBackgroundColor,
        contentColor = assetContentColorFor(cardBackgroundColor),
        elevation = 4.dp,
        shape = CapitalTheme.shapes.extraLarge
    ) {
        val focusManager = LocalFocusManager.current

        val amountValue = rememberSaveable(amount) { mutableStateOf(amount) }
        val nameValue = rememberSaveable(name) { mutableStateOf(name) }

        val onCardBackgroundColor = assetOnBackgroundColorFor(cardBackgroundColor)
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (tfName, atfAmount, bIcon) = createRefs()
            CTextField(
                modifier = Modifier.constrainAs(tfName) {
                    linkTo(start = bIcon.end, end = parent.end, startMargin = 16.dp, endMargin = 16.dp)
                    centerVerticallyTo(bIcon)
                    width = Dimension.fillToConstraints
                },
                value = nameValue.value,
                backgroundColor = onCardBackgroundColor,
                singleLine = true,
                onValueChange = {
                    nameValue.value = it
                    onNameChange.invoke(it)
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                })
            )
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(color = onCardBackgroundColor, shape = CircleShape)
                    .clickable { onIconClick.invoke() }
                    .constrainAs(bIcon) {
                        start.linkTo(parent.start, margin = 16.dp)
                        top.linkTo(parent.top, margin = 16.dp)
                    }
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = icon.getImageVector(),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = LocalContentColor.current)
                )
            }
            CAmountTextField(
                modifier = Modifier.constrainAs(atfAmount) {
                    linkTo(start = parent.start, end = parent.end, startMargin = 16.dp, endMargin = 16.dp)
                    top.linkTo(bIcon.bottom, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    width = Dimension.fillToConstraints
                },
                amount = amountValue.value,
                backgroundColor = onCardBackgroundColor,
                onValueChange = {
                    amountValue.value = it
                    onAmountChange.invoke(it)
                },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                })

            )
        }
    }
}

@Preview
@Composable
private fun AssetEditableCardLight() {
    CPreview {
        Box(
            modifier = Modifier
                .background(CapitalTheme.colors.background)
                .padding(32.dp)
        ) {
            AssetEditableCard(
                name = "Tinkoff black",
                amount = 12444.32,
                icon = AssetIcon.TINKOFF,
                color = AssetColor.TINKOFF,
                onIconClick = {},
                onNameChange = {},
                onAmountChange = {}
            )
        }
    }
}

@Preview
@Composable
private fun AssetEditableCardDark() {
    CPreview(isDark = true) {
        Box(
            modifier = Modifier
                .background(CapitalTheme.colors.background)
                .padding(32.dp)
        ) {
            AssetEditableCard(
                name = "Sberbank",
                amount = 100000.0,
                icon = AssetIcon.SBER,
                color = AssetColor.SBER,
                onIconClick = {},
                onNameChange = {},
                onAmountChange = {}
            )
        }
    }
}
