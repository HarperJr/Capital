package com.harper.capital.overview.asset.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harper.capital.overview.R
import com.harper.capital.spec.domain.Currency
import com.harper.core.component.ComposablePreview
import com.harper.core.component.Separator
import com.harper.core.component.TextField
import com.harper.core.ext.formatCurrencyName
import com.harper.core.ext.formatCurrencySymbol
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme

@Composable
fun CurrencyBottomSheet(modifier: Modifier = Modifier, currencies: List<Currency>) {
    val searchQuery = rememberSaveable { mutableStateOf("") }
    val filteredCurrencies = remember(currencies, searchQuery.value) {
        currencies.filter {
            searchQuery.value.isEmpty() ||
                    it.name.formatCurrencyName().contains(searchQuery.value, ignoreCase = true)
        }
    }
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = stringResource(id = R.string.select_currency),
            style = CapitalTheme.typography.title,
            color = CapitalTheme.colors.onBackground
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = searchQuery.value,
            placeholder = stringResource(id = R.string.search),
            leadingIcon = {
                Image(
                    modifier = Modifier.padding(end = 8.dp),
                    imageVector = CapitalIcons.Search,
                    colorFilter = ColorFilter.tint(color = CapitalColors.Boulder),
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
        Separator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(filteredCurrencies) {
                CurrencyItem(currency = it)
            }
        }
    }
}

@Composable
private fun CurrencyItem(modifier: Modifier = Modifier, currency: Currency) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier
                    .weight(1f, fill = true)
                    .padding(16.dp),
                text = currency.name.formatCurrencyName(),
                style = CapitalTheme.typography.regular,
                color = CapitalTheme.colors.onBackground
            )
            Text(
                modifier = Modifier
                    .padding(16.dp),
                text = currency.name.formatCurrencySymbol(),
                style = CapitalTheme.typography.regular,
                color = CapitalTheme.colors.onBackground
            )
        }
    }
}

@Preview
@Composable
fun CurrencyBottomSheetLight() {
    ComposablePreview {
        Box(modifier = Modifier.background(color = CapitalTheme.colors.background)) {
            CurrencyBottomSheet(currencies = Currency.values().toList())
        }
    }
}

@Preview
@Composable
fun CurrencyBottomSheetDark() {
    ComposablePreview(isDark = true) {
        Box(modifier = Modifier.background(color = CapitalTheme.colors.background)) {
            CurrencyBottomSheet(currencies = Currency.values().toList())
        }
    }
}