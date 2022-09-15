package com.harper.capital.asset.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harper.capital.R
import com.harper.capital.domain.model.AccountColor
import com.harper.capital.domain.model.AccountIcon
import com.harper.capital.domain.model.Currency
import com.harper.capital.ext.accountBackgroundColor
import com.harper.capital.ext.accountContentColorFor
import com.harper.capital.ext.accountGradientBackgroundColor
import com.harper.capital.ext.accountOnBackgroundColorFor
import com.harper.capital.ext.getImageVector
import com.harper.core.component.CAmountTextField
import com.harper.core.component.CHorizontalSpacer
import com.harper.core.component.CPreview
import com.harper.core.component.CTextField
import com.harper.core.component.CVerticalSpacer
import com.harper.core.ext.compose.ACCOUNT_CARD_ASPECT_RATIO
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalTheme

@Composable
fun AssetEditableCard(
    modifier: Modifier = Modifier,
    name: String,
    balance: Double,
    icon: AccountIcon,
    color: AccountColor,
    currency: Currency,
    onIconClick: () -> Unit,
    onAmountChange: (Double) -> Unit,
    onNameChange: (String) -> Unit
) {
    val cardBackgroundColor = accountBackgroundColor(color)
    Card(
        modifier = modifier.aspectRatio(ACCOUNT_CARD_ASPECT_RATIO),
        backgroundColor = CapitalColors.Transparent,
        contentColor = accountContentColorFor(cardBackgroundColor),
        elevation = 0.dp,
        shape = CapitalTheme.shapes.extraLarge
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(brush = accountGradientBackgroundColor(color))) {
            Image(
                modifier = Modifier.fillMaxSize(),
                imageVector = ImageVector.vectorResource(id = R.drawable.bg_card_whiteness),
                contentDescription = null,
                alignment = Alignment.CenterEnd
            )
            val focusManager = LocalFocusManager.current
            val overCardBackgroundColor = accountOnBackgroundColorFor(cardBackgroundColor)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(CapitalTheme.dimensions.side)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CTextField(
                        modifier = Modifier.weight(1f),
                        value = name,
                        placeholder = stringResource(id = R.string.asset_name_hint),
                        backgroundColor = overCardBackgroundColor,
                        singleLine = true,
                        onValueChange = { onNameChange.invoke(it) },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            capitalization = KeyboardCapitalization.Words,
                            imeAction = ImeAction.Next,
                            autoCorrect = true
                        ),
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        })
                    )
                    CVerticalSpacer(width = CapitalTheme.dimensions.medium)
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(color = overCardBackgroundColor, shape = CapitalTheme.shapes.extraLarge)
                            .clickable { onIconClick.invoke() },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            modifier = Modifier.size(CapitalTheme.dimensions.imageMedium),
                            imageVector = icon.getImageVector(),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(color = LocalContentColor.current.copy(alpha = 0.5f))
                        )
                    }
                }
                CHorizontalSpacer(height = CapitalTheme.dimensions.side)
                CAmountTextField(
                    amount = balance,
                    currencyIso = currency.name,
                    backgroundColor = overCardBackgroundColor,
                    onValueChange = { onAmountChange.invoke(it) },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
                    })

                )
            }
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
                balance = 12444.0,
                icon = AccountIcon.TINKOFF,
                color = AccountColor.TINKOFF,
                currency = Currency.RUB,
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
                balance = 100000.0,
                icon = AccountIcon.SBER,
                color = AccountColor.SBER,
                currency = Currency.RUB,
                onIconClick = {},
                onNameChange = {},
                onAmountChange = {}
            )
        }
    }
}
