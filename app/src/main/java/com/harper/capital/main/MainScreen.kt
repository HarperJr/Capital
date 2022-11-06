package com.harper.capital.main

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.BottomSheetScaffoldDefaults
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.harper.capital.R
import com.harper.capital.domain.model.AccountColor
import com.harper.capital.domain.model.AccountPresentation
import com.harper.capital.main.component.ActionCard
import com.harper.capital.main.component.AssetCard
import com.harper.capital.main.component.AssetCell
import com.harper.capital.main.component.AssetMenu
import com.harper.capital.main.component.AssetSummaryCard
import com.harper.capital.main.component.FavoriteTransferTransactionItem
import com.harper.capital.main.domain.model.Summary
import com.harper.capital.main.model.MainBottomSheet
import com.harper.capital.main.model.MainBottomSheetState
import com.harper.capital.main.model.MainEvent
import com.harper.capital.main.model.MainState
import com.harper.core.component.CAmountText
import com.harper.core.component.CBottomSheetScaffold
import com.harper.core.component.CHorizontalSpacer
import com.harper.core.component.CLoaderLayout
import com.harper.core.component.CPreview
import com.harper.core.component.CScaffold
import com.harper.core.component.CToolbar
import com.harper.core.component.Menu
import com.harper.core.component.MenuItem
import com.harper.core.ext.TimePattern
import com.harper.core.ext.compose.ACCOUNT_CARD_ASPECT_RATIO
import com.harper.core.ext.compose.fullyVisibleItemIndex
import com.harper.core.ext.formatBy
import com.harper.core.ext.formatWithCurrencySymbol
import com.harper.core.ext.orElse
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme
import com.harper.core.ui.ComponentViewModel
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import java.time.LocalDate

private const val ADD_ASSET_MENU_ITEM_ID = 0
private const val SETTINGS_MENU_ITEM_ID = 1

@Composable
@OptIn(ExperimentalSnapperApi::class, ExperimentalMaterialApi::class)
fun MainScreen(viewModel: ComponentViewModel<MainState, MainEvent>) {
    val state by viewModel.state.collectAsState()
    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)

    CLoaderLayout(isLoading = state.isLoading, loaderContent = { MainScreenLoaderContent() }) {
        CBottomSheetScaffold(
            topBar = { MainScreenTopBar(viewModel, summary = state.summary) },
            sheetContent = {
                BottomSheetContent(bottomSheetState = state.bottomSheetState, viewModel = viewModel)
            },
            sheetState = sheetState,
            sheetPeekHeight = if (state.bottomSheetState.isExpended) BottomSheetScaffoldDefaults.SheetPeekHeight * 3 else 0.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = CapitalTheme.dimensions.side),
                verticalArrangement = Arrangement.spacedBy(CapitalTheme.dimensions.side)
            ) {
                when (state.accountPresentation) {
                    AccountPresentation.CAROUSEL -> AccountsCarousel(state, viewModel)
                    AccountPresentation.LIST -> AccountsList(state, viewModel)
                }
                val actionsListState = rememberLazyListState()
                val actionCards = state.actionCards

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    state = actionsListState,
                    flingBehavior = rememberSnapperFlingBehavior(actionsListState, endContentPadding = CapitalTheme.dimensions.large),
                    horizontalArrangement = Arrangement.spacedBy(CapitalTheme.dimensions.side),
                    contentPadding = PaddingValues(horizontal = CapitalTheme.dimensions.large)
                ) {
                    items(actionCards) {
                        ActionCard(
                            title = it.title,
                            backgroundColor = it.backgroundColor,
                            backgroundImageRes = it.backgroundImageRes
                        ) {
                            viewModel.onEvent(MainEvent.ActionCardClick(it.type))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AccountsList(state: MainState, viewModel: ComponentViewModel<MainState, MainEvent>, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        state.accounts.forEach {
            AssetCell(
                modifier = Modifier.fillMaxWidth(),
                account = it,
                onHistoryClick = { viewModel.onEvent(MainEvent.HistoryClick(it)) },
                onIncomeClick = { viewModel.onEvent(MainEvent.IncomeClick(it)) },
                onExpenseClick = { viewModel.onEvent(MainEvent.ExpenseClick(it)) },
                onEditClick = { viewModel.onEvent(MainEvent.EditClick(it)) },
                onSwiped = { }
            )
        }
    }
}

@OptIn(ExperimentalSnapperApi::class)
@Composable
private fun AccountsCarousel(state: MainState, viewModel: ComponentViewModel<MainState, MainEvent>, modifier: Modifier = Modifier) {
    val accountListState = rememberLazyListState()
    var selectedAssetIndex by rememberSaveable { mutableStateOf(accountListState.firstVisibleItemIndex) }
    LaunchedEffect(Unit) {
        snapshotFlow { accountListState.layoutInfo }
            .collect {
                val fullyVisibleIndex = it.fullyVisibleItemIndex()
                if (fullyVisibleIndex != -1) {
                    selectedAssetIndex = fullyVisibleIndex
                }
            }
    }
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        state = accountListState,
        flingBehavior = rememberSnapperFlingBehavior(
            lazyListState = accountListState,
            endContentPadding = CapitalTheme.dimensions.large
        ),
        horizontalArrangement = Arrangement.spacedBy(CapitalTheme.dimensions.side),
        contentPadding = PaddingValues(horizontal = CapitalTheme.dimensions.large)
    ) {
        items(state.accounts) {
            AssetCard(modifier = Modifier.fillParentMaxWidth(), account = it)
        }
        item {
            AssetSummaryCard(modifier = Modifier.fillParentMaxWidth(), summary = state.summary)
        }
    }
    val selectedAsset = state.accounts.getOrNull(selectedAssetIndex)
    AssetMenu(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = CapitalTheme.dimensions.large),
        color = selectedAsset?.color.orElse(AccountColor.VTB_OLD),
        onHistoryClick = { viewModel.onEvent(MainEvent.HistoryClick(selectedAsset)) },
        onIncomeClick = { viewModel.onEvent(MainEvent.IncomeClick(selectedAsset)) },
        onExpenseClick = { viewModel.onEvent(MainEvent.ExpenseClick(selectedAsset)) },
        onEditClick = if (selectedAsset != null) {
            { viewModel.onEvent(MainEvent.EditClick(selectedAsset)) }
        } else null
    )
}

@Composable
private fun BottomSheetContent(
    modifier: Modifier = Modifier,
    bottomSheetState: MainBottomSheetState,
    viewModel: ComponentViewModel<MainState, MainEvent>
) {
    when (bottomSheetState.bottomSheet) {
        is MainBottomSheet -> {
            Column(modifier = modifier.fillMaxWidth()) {
                CHorizontalSpacer(height = CapitalTheme.dimensions.medium)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = CapitalTheme.dimensions.side),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = stringResource(id = R.string.your_most_usable), style = CapitalTheme.typography.bottomSheetTitle)
                    Icon(imageVector = CapitalIcons.Repeat, contentDescription = null)
                }
                CHorizontalSpacer(height = CapitalTheme.dimensions.medium)
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(bottomSheetState.bottomSheet.transactions) {
                        FavoriteTransferTransactionItem(modifier = Modifier.fillParentMaxWidth(), it) {
                            viewModel.onEvent(MainEvent.FavoriteTransferTransactionClick(it))
                        }
                    }
                }
            }
        }
        else -> {}
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
                        style = CapitalTheme.typography.subtitle
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
            CHorizontalSpacer(height = CapitalTheme.dimensions.large)
            Box(
                modifier = Modifier
                    .padding(horizontal = CapitalTheme.dimensions.large)
                    .aspectRatio(ACCOUNT_CARD_ASPECT_RATIO)
                    .placeholder(
                        visible = true,
                        color = CapitalTheme.colors.primaryVariant,
                        shape = CapitalTheme.shapes.extraLarge,
                        highlight = PlaceholderHighlight.shimmer(highlightColor = CapitalColors.White)
                    )
            )
            CHorizontalSpacer(height = CapitalTheme.dimensions.large)
            Box(
                modifier = Modifier
                    .padding(horizontal = CapitalTheme.dimensions.large)
                    .height(40.dp)
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
fun MainScreenTopBar(viewModel: ComponentViewModel<MainState, MainEvent>, summary: Summary) {
    CToolbar(
        content = {
            Column(modifier = Modifier.padding(horizontal = CapitalTheme.dimensions.side)) {
                CAmountText(
                    amount = summary.balance,
                    currencyIso = summary.currency.name,
                    style = CapitalTheme.typography.header,
                    color = CapitalTheme.colors.onBackground
                )
                val expensesText = if (summary.expenses == 0.0) {
                    stringResource(id = R.string.no_expenses_in_mount, LocalDate.now().formatBy(TimePattern.LLLL))
                } else {
                    stringResource(
                        id = R.string.expenses_in_month,
                        summary.expenses.formatWithCurrencySymbol(summary.currency.name),
                        LocalDate.now().formatBy(TimePattern.LLLL)
                    )
                }
                Text(
                    text = expensesText,
                    style = CapitalTheme.typography.titleSmall,
                    color = CapitalTheme.colors.secondary
                )
            }
        },
        menu = Menu(
            listOf(
                MenuItem(ADD_ASSET_MENU_ITEM_ID, CapitalIcons.NewAsset),
                MenuItem(SETTINGS_MENU_ITEM_ID, CapitalIcons.Settings)
            )
        ),
        onMenuItemClick = { itemId ->
            when (itemId) {
                ADD_ASSET_MENU_ITEM_ID -> viewModel.onEvent(MainEvent.NewAssetClick)
                SETTINGS_MENU_ITEM_ID -> viewModel.onEvent(MainEvent.SettingsClick)
            }
        }
    )
}

@Preview(showBackground = true, name = "Content dark", uiMode = UI_MODE_NIGHT_NO)
@Preview(showBackground = true, name = "Content light", uiMode = UI_MODE_NIGHT_YES)
@Preview(showBackground = true, name = "Content side small", device = Devices.PIXEL)
@Composable
private fun ContentLight() {
    CPreview {
        MainScreen(MainMockViewModel())
    }
}

