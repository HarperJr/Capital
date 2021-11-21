package com.harper.capital.overview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harper.capital.overview.domain.model.Card
import com.harper.capital.overview.domain.model.Currency
import com.harper.core.component.ComposablePreview
import com.harper.core.component.Toolbar
import com.harper.core.ext.cast
import com.harper.core.theme.CapitalTheme
import com.harper.core.ui.ComponentFragment

class OverviewFragment : ComponentFragment<OverviewViewModel>() {
    override val viewModel: OverviewViewModel by injectViewModel()

    override fun content(): @Composable () -> Unit = {
        val state by viewModel.state.collectAsState()
        when (state) {
            is OverviewState.Loading -> LoadingPlaceholder()
            is OverviewState.Data -> Overview(state.cast())
        }
    }

    companion object {

        fun newInstance(): OverviewFragment = OverviewFragment()
    }
}

@Composable
private fun LoadingPlaceholder() {

}

@Composable
private fun Overview(state: OverviewState.Data) {
    Scaffold(
        topBar = { OverviewTopBar() }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(CapitalTheme.colors.background)
        ) {
            LazyRow(modifier = Modifier.fillMaxWidth()) {
                items(state.cards) {
                    CardItem(it)
                }
            }
        }
    }
}

@Composable
fun CardItem(card: Card) {
    Card(modifier = Modifier.padding(16.dp), elevation = 4.dp, shape = CapitalTheme.shapes.medium) {
        Column(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
            Text(text = card.name)
            Text(text = card.amount.toString())
        }
    }
}

@Composable
fun OverviewTopBar() {
    Toolbar()
}

@Preview
@Composable
private fun OverviewLight() {
    ComposablePreview {
        Overview(state = OverviewState.Data(cards = listOf(Card("Tinkoff", 2044.44, Currency.RUR))))
    }
}

@Preview
@Composable
private fun OverviewDark() {
    ComposablePreview(isDark = true) {
        Overview(state = OverviewState.Data(cards = listOf(Card("Tinkoff", 2044.44, Currency.RUR))))
    }
}
