package com.harper.capital.bottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.imePadding
import com.harper.capital.R
import com.harper.capital.domain.model.Currency
import com.harper.core.component.CTextField
import com.harper.core.component.CPreview
import com.harper.core.component.CHorizontalSpacer
import com.harper.core.component.CSeparator
import com.harper.core.ext.formatCurrencyName
import com.harper.core.ext.formatCurrencySymbol
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme

@Composable
fun CurrencyBottomSheet(
    modifier: Modifier = Modifier,
    currencies: List<Currency>,
    selectedCurrency: Currency,
    onCurrencySelect: (Currency) -> Unit
) {
    val searchQuery = rememberSaveable { mutableStateOf("") }
    val filteredCurrencies = remember(currencies, searchQuery.value) {
        currencies.filter {
            searchQuery.value.isEmpty() ||
                    it.name.formatCurrencyName().contains(searchQuery.value, ignoreCase = true)
        }
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .imePadding()
    ) {
        CHorizontalSpacer(height = 8.dp)
        CTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = searchQuery.value,
            placeholder = stringResource(id = R.string.search),
            leadingIcon = {
                Image(
                    modifier = Modifier.padding(end = 8.dp),
                    imageVector = CapitalIcons.Search,
                    colorFilter = ColorFilter.tint(color = CapitalColors.GreyDark),
                    contentDescription = null
                )
            },
            onValueChange = { searchQuery.value = it }
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
        )
        CSeparator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(filteredCurrencies) {
                CurrencyItem(currency = it, isSelected = it == selectedCurrency) {
                    onCurrencySelect.invoke(it)
                }
            }
        }
    }
}

@Composable
private fun CurrencyItem(modifier: Modifier = Modifier, currency: Currency, isSelected: Boolean, onClick: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        val textStyle = if (isSelected) {
            CapitalTheme.typography.regular.copy(fontWeight = FontWeight.Bold)
        } else {
            CapitalTheme.typography.regular
        }
        val textColor = if (isSelected) CapitalColors.Blue else CapitalTheme.colors.onBackground

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { onClick.invoke() })
        ) {
            Text(
                modifier = Modifier
                    .weight(1f, fill = true)
                    .padding(16.dp),
                text = currency.name.formatCurrencyName(),
                style = textStyle,
                color = textColor
            )
            Text(
                modifier = Modifier
                    .padding(16.dp),
                text = currency.name.formatCurrencySymbol(),
                style = textStyle,
                color = textColor
            )
        }
    }
}

@Preview
@Composable
private fun CurrencyBottomSheetLight() {
    CPreview {
        Box(modifier = Modifier.background(color = CapitalTheme.colors.background)) {
            CurrencyBottomSheet(
                currencies = Currency.values().toList(),
                selectedCurrency = Currency.USD,
                onCurrencySelect = {}
            )
        }
    }
}

@Preview
@Composable
private fun CurrencyBottomSheetDark() {
    CPreview(isDark = true) {
        Box(modifier = Modifier.background(color = CapitalTheme.colors.background)) {
            CurrencyBottomSheet(
                currencies = Currency.values().toList(),
                selectedCurrency = Currency.RUB,
                onCurrencySelect = {}
            )
        }
    }
}
