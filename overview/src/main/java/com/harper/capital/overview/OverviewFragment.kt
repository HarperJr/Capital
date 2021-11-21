package com.harper.capital.overview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harper.capital.overview.component.AssetCard
import com.harper.capital.overview.domain.model.Asset
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
                items(state.assets) {
                    AssetCard(
                        modifier = Modifier
                            .size(width = 300.dp, height = 156.dp)
                            .padding(horizontal = 16.dp),
                        asset = it
                    )
                }
            }
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
        Overview(
            state = OverviewState.Data(
                assets = listOf(
                    Asset(
                        "Tinkoff Credit",
                        2044.44,
                        Currency.RUR
                    ),
                    Asset(
                        "Tinkoff USD",
                        24.44,
                        Currency.USD
                    )
                )
            )
        )
    }
}

@Preview
@Composable
private fun OverviewDark() {
    ComposablePreview(isDark = true) {
        Overview(
            state = OverviewState.Data(
                assets = listOf(
                    Asset(
                        "Tinkoff Credit",
                        2044.44,
                        Currency.RUR
                    ),
                    Asset(
                        "Tinkoff USD",
                        24.44,
                        Currency.USD
                    )
                )
            )
        )
    }
}
