package com.harper.capital.overview.asset.ui.component

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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.harper.capital.overview.R
import com.harper.core.component.AmountTextField
import com.harper.core.component.ComposablePreview
import com.harper.core.component.TextField
import com.harper.core.ext.compose.assetCardSize
import com.harper.core.ext.formatCurrencySymbol
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalTheme

@Composable
fun AssetEditableCard(modifier: Modifier = Modifier, currencyIso: String, onCurrencyClick: () -> Unit = {}) {
    Card(
        modifier = modifier,
        backgroundColor = CapitalColors.Thunder,
        elevation = 4.dp,
        shape = CapitalTheme.shapes.extraLarge
    ) {
        val amountValue = rememberSaveable { mutableStateOf(0.0) }
        val nameValue = rememberSaveable { mutableStateOf("") }
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (name, amount, currency, icon) = createRefs()
            TextField(
                modifier = Modifier.constrainAs(name) {
                    linkTo(start = icon.end, end = parent.end, startMargin = 16.dp, endMargin = 16.dp)
                    linkTo(top = icon.top, bottom = icon.bottom)
                    width = Dimension.fillToConstraints
                },
                value = nameValue.value,
                placeholder = stringResource(id = R.string.asset_enter_name_hint),
                textStyle = CapitalTheme.typography.regular,
                textColor = CapitalColors.White,
                backgroundColor = CapitalColors.CodGray,
                singleLine = true,
                onValueChange = { nameValue.value = it },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(color = CapitalColors.CodGray, shape = CircleShape)
                    .clickable { onCurrencyClick.invoke() }
                    .constrainAs(icon) {
                        start.linkTo(parent.start, margin = 16.dp)
                        top.linkTo(parent.top, margin = 16.dp)
                    }
            )
            AmountTextField(
                modifier = Modifier.constrainAs(amount) {
                    linkTo(start = parent.start, end = parent.end, startMargin = 16.dp, endMargin = 16.dp)
                    top.linkTo(name.bottom, margin = 16.dp)
                    end.linkTo(currency.start, margin = 16.dp)
                    width = Dimension.fillToConstraints
                },
                amount = amountValue.value,
                textStyle = CapitalTheme.typography.regular,
                textColor = CapitalColors.White,
                backgroundColor = CapitalColors.CodGray,
                onValueChange = { amountValue.value = it },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)

            )
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(color = CapitalColors.CodGray, shape = CircleShape)
                    .clickable { onCurrencyClick.invoke() }
                    .constrainAs(currency) {
                        end.linkTo(parent.end, margin = 16.dp)
                        linkTo(top = amount.top, bottom = amount.bottom)
                    }
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = currencyIso.formatCurrencySymbol(),
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
            AssetEditableCard(currencyIso = "RUB")
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
            AssetEditableCard(currencyIso = "USD")
        }
    }
}
