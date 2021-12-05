package com.harper.capital.overview.asset.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.harper.capital.overview.R
import com.harper.capital.spec.domain.AssetColor
import com.harper.capital.spec.domain.AssetIcon
import com.harper.capital.spec.domain.Currency
import com.harper.core.component.AmountTextField
import com.harper.core.component.ComposablePreview
import com.harper.core.component.TextField
import com.harper.core.ext.compose.assetCardSize
import com.harper.core.ext.formatCurrencySymbol
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalTheme

private const val fieldAlpha = 0.6f

@Composable
fun AssetEditableCard(
    modifier: Modifier = Modifier,
    name: String,
    amount: Double,
    currency: Currency,
    icon: AssetIcon,
    color: AssetColor,
    onCurrencyClick: () -> Unit,
    onIconClick: () -> Unit,
    onAmountChange: (Double) -> Unit,
    onNameChange: (String) -> Unit
) {
    Card(
        modifier = modifier,
        backgroundColor = Color(color.value),
        elevation = 4.dp,
        shape = CapitalTheme.shapes.extraLarge
    ) {
        val amountValue = rememberSaveable(amount) { mutableStateOf(amount) }
        val nameValue = rememberSaveable(name) { mutableStateOf(name) }
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (tfName, atfAmount, bCurrency, bIcon) = createRefs()
            TextField(
                modifier = Modifier.constrainAs(tfName) {
                    linkTo(start = bIcon.end, end = parent.end, startMargin = 16.dp, endMargin = 16.dp)
                    centerVerticallyTo(bIcon)
                    width = Dimension.fillToConstraints
                },
                value = nameValue.value,
                placeholder = stringResource(id = R.string.asset_enter_name_hint),
                textColor = CapitalColors.White,
                backgroundColor = CapitalColors.CodGray.copy(alpha = fieldAlpha),
                singleLine = true,
                onValueChange = {
                    nameValue.value = it
                    onNameChange.invoke(it)
                },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(color = CapitalColors.CodGray.copy(alpha = fieldAlpha), shape = CircleShape)
                    .clickable { onCurrencyClick.invoke() }
                    .constrainAs(bIcon) {
                        start.linkTo(parent.start, margin = 16.dp)
                        top.linkTo(parent.top, margin = 16.dp)
                    }
                    .clickable { onIconClick.invoke() }
            ) {
                Image(modifier = Modifier.fillMaxSize(), imageVector = icon.getImageVector(), contentDescription = null)
            }
            AmountTextField(
                modifier = Modifier.constrainAs(atfAmount) {
                    linkTo(start = parent.start, end = parent.end, startMargin = 16.dp, endMargin = 16.dp)
                    top.linkTo(tfName.bottom, margin = 16.dp)
                    end.linkTo(bCurrency.start, margin = 16.dp)
                    width = Dimension.fillToConstraints
                },
                amount = amountValue.value,
                textColor = CapitalColors.White,
                backgroundColor = CapitalColors.CodGray.copy(alpha = fieldAlpha),
                onValueChange = {
                    amountValue.value = it
                    onAmountChange.invoke(it)
                },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)

            )
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(color = CapitalColors.CodGray.copy(alpha = fieldAlpha), shape = CircleShape)
                    .clickable { onCurrencyClick.invoke() }
                    .constrainAs(bCurrency) {
                        end.linkTo(parent.end, margin = 16.dp)
                        centerVerticallyTo(atfAmount)
                    }
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = currency.name.formatCurrencySymbol(),
                    style = CapitalTheme.typography.regular,
                    color = CapitalColors.White
                )
            }
        }
    }
}

@Preview
@Composable
private fun AssetEditableCardLight() {
    ComposablePreview {
        Box(
            modifier = Modifier
                .assetCardSize()
                .background(CapitalTheme.colors.background)
                .padding(32.dp)
        ) {
            AssetEditableCard(
                name = "Tinkoff black",
                amount = 12444.32,
                currency = Currency.RUB,
                icon = AssetIcon.TINKOFF,
                color = AssetColor.DARK_TINKOFF,
                onCurrencyClick = {},
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
    ComposablePreview(isDark = true) {
        Box(
            modifier = Modifier
                .assetCardSize()
                .background(CapitalTheme.colors.background)
                .padding(32.dp)
        ) {
            AssetEditableCard(
                name = "Sberbank",
                amount = 100000.0,
                currency = Currency.RUB,
                icon = AssetIcon.SBER,
                color = AssetColor.GREEN_SBER,
                onCurrencyClick = {},
                onIconClick = {},
                onNameChange = {},
                onAmountChange = {}
            )
        }
    }
}
