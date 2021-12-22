package com.harper.capital.overview

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.harper.capital.R
import com.harper.capital.domain.model.Account
import com.harper.capital.overview.component.AssetAccountedCard
import com.harper.capital.overview.component.AssetCard
import com.harper.capital.overview.component.CardToolbar
import com.harper.capital.overview.model.OverviewEvent
import com.harper.capital.overview.model.OverviewState
import com.harper.capital.overview.model.PreviewStateProvider
import com.harper.core.component.AmountText
import com.harper.core.component.ComposablePreview
import com.harper.core.component.Separator
import com.harper.core.component.Toolbar
import com.harper.core.ext.cast
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalTheme
import com.harper.core.ui.ComponentFragment
import com.harper.core.ui.EventSender
import com.harper.core.ui.MockEventSender
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior

class OverviewFragment : ComponentFragment<OverviewViewModel>(), EventSender<OverviewEvent> {
    override val viewModel: OverviewViewModel by injectViewModel()

    override fun content(): @Composable () -> Unit = {
        val state by viewModel.state.collectAsState()
        when (state) {
            is OverviewState.Loading -> LoadingPlaceholder()
            is OverviewState.Data -> Overview(state.cast(), this)
        }
    }

    companion object {

        fun newInstance(): OverviewFragment = OverviewFragment()
    }
}

@Composable
private fun LoadingPlaceholder() {

}

@OptIn(ExperimentalPagerApi::class, dev.chrisbanes.snapper.ExperimentalSnapperApi::class)
@Composable
private fun Overview(state: OverviewState.Data, es: EventSender<OverviewEvent>) {
    Scaffold(
        topBar = { OverviewTopBar(account = state.account) }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(CapitalTheme.colors.background)
        ) {
            Separator(modifier = Modifier.padding(horizontal = 16.dp))
            Spacer(
                modifier = Modifier
                    .height(24.dp)
                    .fillMaxWidth()
            )
            Image(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 16.dp)
                    .clickable { es.send(OverviewEvent.AddAssetClick) },
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_add_asset),
                contentDescription = null
            )
            Spacer(
                modifier = Modifier
                    .height(16.dp)
                    .fillMaxWidth()
            )
            val assetListState = rememberLazyListState()
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                state = assetListState,
                flingBehavior = rememberSnapperFlingBehavior(lazyListState = assetListState)
            ) {
                items(state.assets) {
                    AssetCard(
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .padding(horizontal = 16.dp),
                        asset = it
                    )
                }
                item {
                    AssetAccountedCard(
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .padding(horizontal = 16.dp),
                        account = state.account
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .height(32.dp)
                    .fillMaxWidth()
            )
            if (state.assets.isNotEmpty()) {
                CardToolbar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    color = CapitalColors.Thunder,
                    onIncomeClick = {},
                    onExpenseClick = {},
                    onEditClick = {}
                )
            }
        }
    }
}

@Composable
fun OverviewTopBar(account: Account) {
    Toolbar(title = {
        AmountText(
            modifier = Modifier.padding(horizontal = 16.dp),
            amount = account.amount,
            currencyIso = account.currency.name,
            style = CapitalTheme.typography.title,
            color = CapitalTheme.colors.onBackground
        )
    })
}

@Preview
@Composable
private fun OverviewLight(@PreviewParameter(PreviewStateProvider::class) mockState: OverviewState.Data) {
    ComposablePreview {
        Overview(
            state = mockState,
            MockEventSender()
        )
    }
}

@Preview
@Composable
private fun OverviewDark(@PreviewParameter(PreviewStateProvider::class) mockState: OverviewState.Data) {
    ComposablePreview(isDark = true) {
        Overview(
            state = mockState,
            MockEventSender()
        )
    }
}
