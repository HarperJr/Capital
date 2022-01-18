package com.harper.capital.main

import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListLayoutInfo
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.harper.capital.R
import com.harper.capital.domain.model.Account
import com.harper.capital.main.component.AssetAccountedCard
import com.harper.capital.main.component.AssetCard
import com.harper.capital.main.component.AssetToolbar
import com.harper.capital.main.model.MainEvent
import com.harper.capital.main.model.MainState
import com.harper.capital.main.model.PreviewStateProvider
import com.harper.core.component.AmountText
import com.harper.core.component.ComposablePreview
import com.harper.core.component.HorizontalSpacer
import com.harper.core.component.Menu
import com.harper.core.component.MenuIcon
import com.harper.core.component.MenuItem
import com.harper.core.component.Toolbar
import com.harper.core.ext.cast
import com.harper.core.ext.formatWithCurrencySymbol
import com.harper.core.ext.orElse
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme
import com.harper.core.ui.ComponentFragment
import com.harper.core.ui.EventSender
import com.harper.core.ui.MockEventSender
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import kotlin.math.abs
import kotlinx.coroutines.launch

private const val ADD_ASSET_MENU_ITEM_ID = 0
private const val SETTINGS_MENU_ITEM_ID = 1

class MainFragment : ComponentFragment<MainViewModel>(), EventSender<MainEvent> {
    override val viewModel: MainViewModel by injectViewModel()

    override fun content(): @Composable () -> Unit = {
        val state by viewModel.state.collectAsState()
        when (state) {
            is MainState.Loading -> LoadingPlaceholder()
            is MainState.Data -> Content(state.cast(), this)
        }
    }

    companion object {

        fun newInstance(): MainFragment = MainFragment()
    }
}

@Composable
private fun LoadingPlaceholder() {
    // TODO implement
}

@OptIn(ExperimentalPagerApi::class, dev.chrisbanes.snapper.ExperimentalSnapperApi::class)
@Composable
private fun Content(state: MainState.Data, es: EventSender<MainEvent>) {
    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        backgroundColor = CapitalTheme.colors.background,
        topBar = { OverviewTopBar(account = state.account, es) }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxWidth()) {
                HorizontalSpacer(height = 24.dp)

                val assetListState = rememberLazyListState()
                val isDragged by assetListState.interactionSource.collectIsDraggedAsState()
                val selectedAssetIndex = rememberSaveable { mutableStateOf(-1) }
                LaunchedEffect(assetListState.isScrollInProgress, isDragged) {
                    launch {
                        selectedAssetIndex.value = assetListState.layoutInfo.fullyVisibleItemIndex()
                    }
                }
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
                                .padding(horizontal = 24.dp),
                            asset = it
                        )
                    }
                    item {
                        AssetAccountedCard(
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .padding(horizontal = 24.dp),
                            account = state.account
                        )
                    }
                }
                HorizontalSpacer(height = 24.dp)
                val selectedAsset = state.assets.getOrNull(selectedAssetIndex.value)
                AssetToolbar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    color = selectedAsset?.color?.let { Color(it.value) }.orElse(CapitalColors.CodGray),
                    onHistoryClick = { es.send(MainEvent.HistoryClick(selectedAsset)) },
                    onIncomeClick = { es.send(MainEvent.IncomeClick(selectedAsset)) },
                    onExpenseClick = { es.send(MainEvent.ExpenseClick(selectedAsset)) },
                    onEditClick = if (selectedAsset != null) {
                        { es.send(MainEvent.EditClick(selectedAsset)) }
                    } else null
                )
            }
            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(24.dp),
                onClick = { },
                backgroundColor = CapitalTheme.colors.background
            ) {
                MenuIcon(imageVector = CapitalIcons.NewAsset, color = CapitalColors.Blue)
            }
        }
    }
}

@Composable
fun OverviewTopBar(account: Account, es: EventSender<MainEvent>) {
    Toolbar(
        content = {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                AmountText(
                    amount = account.amount,
                    currencyIso = account.currency.name,
                    style = CapitalTheme.typography.header,
                    color = CapitalTheme.colors.onBackground
                )
                Text(
                    text = stringResource(
                        id = R.string.expenses_in_month,
                        (-23424.42).formatWithCurrencySymbol(account.currency.name),
                        "January"
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

private fun LazyListLayoutInfo.fullyVisibleItemIndex(): Int {
    val viewportCenter = (viewportEndOffset + viewportStartOffset) / 2f
    return visibleItemsInfo
        .firstOrNull {
            abs((it.offset + it.size / 2f) - viewportCenter) <= viewportCenter
        }?.index.orElse(-1)
}


@Preview(showBackground = true, name = "Content light")
@Composable
private fun ContentLight(@PreviewParameter(PreviewStateProvider::class) mockState: MainState.Data) {
    ComposablePreview {
        Content(
            state = mockState,
            MockEventSender()
        )
    }
}

@Preview(showBackground = true, name = "Content dark")
@Composable
private fun ContentDark(@PreviewParameter(PreviewStateProvider::class) mockState: MainState.Data) {
    ComposablePreview(isDark = true) {
        Content(
            state = mockState,
            MockEventSender()
        )
    }
}
