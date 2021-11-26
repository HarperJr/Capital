package com.harper.capital.overview.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.harper.capital.overview.R
import com.harper.capital.overview.ui.component.AssetCard
import com.harper.capital.overview.ui.model.OverviewEvent
import com.harper.capital.overview.ui.model.OverviewState
import com.harper.capital.overview.ui.model.PreviewStateProvider
import com.harper.core.component.Chip
import com.harper.core.component.ComposablePreview
import com.harper.core.component.Toolbar
import com.harper.core.ext.cast
import com.harper.core.ext.format
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalTheme
import com.harper.core.ui.ComponentFragment
import com.harper.core.ui.EventSender
import com.harper.core.ui.MockEventSender

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

@Composable
private fun Overview(state: OverviewState.Data, eventSender: EventSender<OverviewEvent>) {
    Scaffold(
        topBar = { OverviewTopBar() }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(CapitalTheme.colors.background)
        ) {
            if (state.shouldShowAddAssetButton) {
                Spacer(
                    modifier = Modifier
                        .height(16.dp)
                        .fillMaxWidth()
                )
                Chip(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 16.dp),
                    text = {
                        Text(
                            text = stringResource(id = R.string.add),
                            style = CapitalTheme.typography.regular,
                            color = CapitalColors.White
                        )
                    })
            }
            Spacer(
                modifier = Modifier
                    .height(16.dp)
                    .fillMaxWidth()
            )
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(state.assets) {
                    AssetCard(
                        modifier = Modifier
                            .padding(horizontal = 16.dp),
                        asset = it
                    )
                }
                item {
                    Card(
                        modifier = Modifier
                            .size(width = 300.dp, height = 156.dp)
                            .padding(horizontal = 16.dp)
                            .clickable { eventSender.event(OverviewEvent.AddAssetClick) },
                        backgroundColor = CapitalTheme.colors.secondary,
                        elevation = 4.dp,
                        shape = CapitalTheme.shapes.large
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Text(
                                modifier = Modifier.align(Alignment.Center),
                                text = stringResource(id = R.string.add),
                                style = CapitalTheme.typography.header,
                                color = CapitalTheme.colors.onSecondary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OverviewTopBar() {
    Toolbar(title = {
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(
                id = R.string.amount_available,
                123.00.format(com.harper.capital.spec.domain.Currency.RUB.name)
            ),
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
