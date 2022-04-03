package com.harper.capital.analytics

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Tab
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.harper.capital.R
import com.harper.capital.analytics.model.AnalyticsEvent
import com.harper.capital.analytics.model.AnalyticsPage
import com.harper.capital.analytics.model.AnalyticsState
import com.harper.capital.analytics.model.Chart
import com.harper.capital.component.AccountIconRound
import com.harper.capital.domain.model.Account
import com.harper.core.component.CHorizontalSpacer
import com.harper.core.component.CPreview
import com.harper.core.component.CScaffold
import com.harper.core.component.CTabBar
import com.harper.core.component.CToolbarCommon
import com.harper.core.component.CVerticalSpacer
import com.harper.core.component.chart.CLineChart
import com.harper.core.ext.formatWithCurrencySymbol
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalTheme
import com.harper.core.ui.ComponentViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AnalyticsScreen(viewModel: ComponentViewModel<AnalyticsState, AnalyticsEvent>) {
    val state by viewModel.state.collectAsState()
    val pagerState = rememberPagerState(initialPage = state.selectedPage)

    CScaffold(
        topBar = { AnalyticsTopBar(viewModel) }
    ) {
        Column {
            Tabs(modifier = Modifier.fillMaxWidth(), pagerState = pagerState, pages = state.pages) { index ->
                viewModel.onEvent(AnalyticsEvent.PageSelect(index))
            }
            HorizontalPager(modifier = Modifier.fillMaxSize(), count = state.pages.size, state = pagerState) { pageIndex ->
                val page = state.pages[pageIndex]
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {
                        CHorizontalSpacer(height = CapitalTheme.dimensions.side)
                        ChartPageBlock(
                            modifier = Modifier
                                .padding(CapitalTheme.dimensions.side)
                                .fillParentMaxWidth(1f)
                                .fillParentMaxHeight(0.33f),
                            page = page
                        )
                    }
                    items(page.dataSet.accounts) {
                        AccountItem(modifier = Modifier.fillParentMaxWidth(), account = it)
                    }
                }
            }
        }
    }
}

@ExperimentalPagerApi
@Composable
private fun Tabs(modifier: Modifier = Modifier, pages: List<AnalyticsPage>, pagerState: PagerState, onTabSelect: (Int) -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val selectedTabIndex by remember { derivedStateOf { pagerState.currentPage } }
    CTabBar(
        modifier = modifier,
        tabCount = pages.size,
        tab = { index ->
            Tab(
                modifier = Modifier.padding(
                    start = if (index > 0) CapitalTheme.dimensions.medium else 0.dp,
                    end = if (index < pages.size) CapitalTheme.dimensions.medium else 0.dp
                ),
                selected = index == selectedTabIndex,
                selectedContentColor = CapitalTheme.colors.onPrimary,
                unselectedContentColor = CapitalTheme.colors.onPrimary,
                onClick = {
                    coroutineScope.launch {
                        pagerState.scrollToPage(index)
                    }
                }
            ) {
                AnalyticsTab(title = pages[index].title)
            }
        },
        indicator = {
            Box(
                modifier = Modifier
                    .tabIndicatorOffset(it[selectedTabIndex])
                    .fillMaxHeight()
                    .padding(
                        start = if (selectedTabIndex > 0) CapitalTheme.dimensions.medium else 0.dp,
                        end = if (selectedTabIndex < pages.size) CapitalTheme.dimensions.medium else 0.dp
                    )
                    .border(
                        width = 2.dp,
                        color = CapitalTheme.colors.secondary,
                        shape = CapitalTheme.shapes.extraLarge
                    )
            )
        },
        divider = { },
        edgePadding = CapitalTheme.dimensions.side,
        pagerState = pagerState,
        isScrollable = true,
        onTabSelect = onTabSelect
    )
}

@Composable
private fun AnalyticsTab(modifier: Modifier = Modifier, title: String) {
    Row(
        modifier = modifier
            .background(
                color = CapitalTheme.colors.background,
                shape = CapitalTheme.shapes.extraLarge
            )
            .border(width = 2.dp, color = CapitalTheme.colors.primaryVariant, shape = CapitalTheme.shapes.extraLarge)
            .padding(CapitalTheme.dimensions.medium)
    ) {
        Box(
            modifier = Modifier
                .size(CapitalTheme.dimensions.imageMedium)
                .background(
                    color = CapitalTheme.colors.onBackground,
                    shape = CapitalTheme.shapes.large
                )
        )
        CVerticalSpacer(width = CapitalTheme.dimensions.medium)
        Text(text = title, style = CapitalTheme.typography.subtitle)
    }
}

@Composable
private fun AccountItem(modifier: Modifier = Modifier, account: Account) {
    Row(
        modifier = modifier.padding(CapitalTheme.dimensions.side),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AccountIconRound(
            size = CapitalTheme.dimensions.imageLarge,
            color = account.color,
            icon = account.icon
        )
        CVerticalSpacer(width = CapitalTheme.dimensions.side)
        Text(
            modifier = Modifier.weight(1f),
            text = account.name,
            style = CapitalTheme.typography.subtitle,
            color = CapitalTheme.colors.textPrimary,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = account.balance.formatWithCurrencySymbol(currencyIso = account.currency.name),
            color = CapitalTheme.colors.textPrimary
        )
    }
}

@Composable
private fun ChartPageBlock(modifier: Modifier = Modifier, page: AnalyticsPage) {
    when (val chart = page.dataSet.chart) {
        is Chart.LineChart -> CLineChart(
            modifier = modifier,
            lineChartData = chart.data
        )
        is Chart.LineLoadingChart,
        is Chart.PieLoadingChart -> Box(
            modifier = modifier
                .placeholder(
                    visible = true,
                    color = CapitalTheme.colors.primaryVariant,
                    shape = CapitalTheme.shapes.large,
                    highlight = PlaceholderHighlight.shimmer(highlightColor = CapitalColors.White)
                )
        )
    }
}

@Composable
private fun AnalyticsTopBar(viewModel: ComponentViewModel<AnalyticsState, AnalyticsEvent>) {
    CToolbarCommon(
        title = stringResource(id = R.string.analytics),
        onNavigationClick = { viewModel.onEvent(AnalyticsEvent.BackClick) }
    )
}

@Preview(showBackground = true)
@Composable
private fun AnalyticsScreenLight() {
    CPreview {
        AnalyticsScreen(AnalyticsMockViewModel())
    }
}

@Preview(showBackground = true)
@Composable
private fun AnalyticsScreenDark() {
    CPreview(isDark = true) {
        AnalyticsScreen(AnalyticsMockViewModel())
    }
}
