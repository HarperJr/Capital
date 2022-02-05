package com.harper.capital.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.harper.capital.R
import com.harper.capital.domain.model.AccountColor
import com.harper.capital.main.component.AssetCard
import com.harper.capital.main.component.AssetMenu
import com.harper.capital.main.component.AssetSummaryCard
import com.harper.capital.main.domain.model.Summary
import com.harper.capital.main.model.MainEvent
import com.harper.capital.main.model.MainState
import com.harper.capital.ui.base.ScreenLayout
import com.harper.core.component.CAmountText
import com.harper.core.component.CHorizontalSpacer
import com.harper.core.component.CIcon
import com.harper.core.component.CLoaderLayout
import com.harper.core.component.CPreview
import com.harper.core.component.CScaffold
import com.harper.core.component.CToolbar
import com.harper.core.component.Menu
import com.harper.core.component.MenuItem
import com.harper.core.ext.compose.assetCardSize
import com.harper.core.ext.compose.fullyVisibleItemIndex
import com.harper.core.ext.formatWithCurrencySymbol
import com.harper.core.ext.orElse
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme
import com.harper.core.ui.ComponentFragment
import com.harper.core.ui.ComponentViewModel
import com.harper.core.ui.EventSender
import com.harper.core.ui.MockEventSender
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import kotlinx.coroutines.flow.collect
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private const val ADD_ASSET_MENU_ITEM_ID = 0
private const val SETTINGS_MENU_ITEM_ID = 1

private val MMMMDateTimeFormatter = DateTimeFormatter.ofPattern("MMMM")

class MainFragment : ComponentFragment<MainViewModel>(), EventSender<MainEvent> {
    override val viewModel: MainViewModel by injectViewModel()

    override fun content(): @Composable () -> Unit = {
        ScreenLayout {
            MainScreenScreen(viewModel, this)
        }
    }

    companion object {

        fun newInstance(): MainFragment = MainFragment()
    }
}

@Composable
@OptIn(ExperimentalPagerApi::class, dev.chrisbanes.snapper.ExperimentalSnapperApi::class)
private fun MainScreenScreen(viewModel: ComponentViewModel<MainState>, es: EventSender<MainEvent>) {
    val state by viewModel.state.collectAsState()

    CLoaderLayout(isLoading = state.isLoading, loaderContent = { MainScreenLoaderContent() }) {
        CScaffold(
            topBar = { OverviewTopBar(summary = state.summary, es) },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { },
                    contentColor = CapitalTheme.colors.secondary,
                    backgroundColor = CapitalTheme.colors.background
                ) {
                    CIcon(imageVector = CapitalIcons.NewAsset)
                }
            }
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    CHorizontalSpacer(height = 24.dp)

                    val assetListState = rememberLazyListState()
                    val selectedAssetIndex =
                        rememberSaveable { mutableStateOf(assetListState.firstVisibleItemIndex) }
                    LaunchedEffect(assetListState) {
                        snapshotFlow { assetListState.layoutInfo }
                            .collect {
                                selectedAssetIndex.value = it.fullyVisibleItemIndex()
                            }
                    }
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth(),
                        state = assetListState,
                        flingBehavior = rememberSnapperFlingBehavior(lazyListState = assetListState)
                    ) {
                        items(state.accounts) {
                            AssetCard(
                                modifier = Modifier
                                    .fillParentMaxWidth()
                                    .padding(horizontal = 24.dp),
                                account = it
                            )
                        }
                        item {
                            AssetSummaryCard(
                                modifier = Modifier
                                    .fillParentMaxWidth()
                                    .padding(horizontal = 24.dp),
                                summary = state.summary
                            )
                        }
                    }
                    CHorizontalSpacer(height = 24.dp)
                    val selectedAsset = state.accounts.getOrNull(selectedAssetIndex.value)
                    AssetMenu(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        color = selectedAsset?.color.orElse(AccountColor.TINKOFF_PLATINUM),
                        onHistoryClick = { es.send(MainEvent.HistoryClick(selectedAsset)) },
                        onIncomeClick = { es.send(MainEvent.IncomeClick(selectedAsset)) },
                        onExpenseClick = { es.send(MainEvent.ExpenseClick(selectedAsset)) },
                        onEditClick = if (selectedAsset != null) {
                            { es.send(MainEvent.EditClick(selectedAsset)) }
                        } else null
                    )
                }
            }
        }
    }
}

@Composable
private fun MainScreenLoaderContent() {
    CScaffold(topBar = {
        CToolbar(
            content = {
                Column(modifier = Modifier.padding(horizontal = CapitalTheme.dimensions.side)) {
                    Text(
                        modifier = Modifier
                            .placeholder(
                                visible = true,
                                color = CapitalTheme.colors.primaryVariant,
                                shape = CapitalTheme.shapes.large,
                                highlight = PlaceholderHighlight.shimmer(highlightColor = CapitalColors.White)
                            ),
                        text = "100 000 000,00 P",
                        style = CapitalTheme.typography.title
                    )
                    CHorizontalSpacer(height = CapitalTheme.dimensions.small)
                    Text(
                        modifier = Modifier.placeholder(
                            visible = true,
                            color = CapitalTheme.colors.primaryVariant,
                            shape = CapitalTheme.shapes.large,
                            highlight = PlaceholderHighlight.shimmer(highlightColor = CapitalColors.White)
                        ),
                        text = "-1 000,00 P in January",
                        style = CapitalTheme.typography.titleSmall
                    )
                }
            }
        )
    }) {
        Column {
            CHorizontalSpacer(height = 24.dp)
            Box(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .assetCardSize()
                    .placeholder(
                        visible = true,
                        color = CapitalTheme.colors.primaryVariant,
                        shape = CapitalTheme.shapes.extraLarge,
                        highlight = PlaceholderHighlight.shimmer(highlightColor = CapitalColors.White)
                    )
            )
        }
    }
}

@Composable
fun OverviewTopBar(summary: Summary, es: EventSender<MainEvent>) {
    CToolbar(
        content = {
            Column(modifier = Modifier.padding(horizontal = CapitalTheme.dimensions.side)) {
                CAmountText(
                    amount = summary.balance,
                    currencyIso = summary.currency.name,
                    style = CapitalTheme.typography.header,
                    color = CapitalTheme.colors.onBackground
                )
                Text(
                    text = stringResource(
                        id = R.string.expenses_in_month,
                        summary.expenses.formatWithCurrencySymbol(summary.currency.name),
                        LocalDate.now().format(MMMMDateTimeFormatter)
                    ),
                    style = CapitalTheme.typography.titleSmall,
                    color = CapitalColors.Blue
                )
            }
        },
        menu = Menu(
            listOf(
                MenuItem(ADD_ASSET_MENU_ITEM_ID, CapitalIcons.AddAsset),
                MenuItem(SETTINGS_MENU_ITEM_ID, CapitalIcons.Settings)
            )
        ),
        onMenuItemClick = { itemId ->
            when (itemId) {
                ADD_ASSET_MENU_ITEM_ID -> es.send(MainEvent.NewAssetClick)
                SETTINGS_MENU_ITEM_ID -> es.send(MainEvent.SettingsClick)
            }
        }
    )
}

@Preview(showBackground = true, name = "Content light")
@Composable
private fun ContentLight() {
    CPreview {
        MainScreenScreen(MainMockViewModel(), MockEventSender())
    }
}

@Preview(showBackground = true, name = "Content dark")
@Composable
private fun MainScreenDark() {
    CPreview(isDark = true) {
        MainScreenScreen(MainMockViewModel(), MockEventSender())
    }
}
