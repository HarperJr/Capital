package com.harper.capital.transaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.harper.capital.R
import com.harper.capital.domain.model.AccountType
import com.harper.capital.transaction.component.AssetSource
import com.harper.capital.transaction.component.NewSource
import com.harper.capital.transaction.model.AccountDataSet
import com.harper.capital.transaction.model.TransactionEvent
import com.harper.capital.transaction.model.TransactionState
import com.harper.core.component.CHorizontalSpacer
import com.harper.core.component.CIcon
import com.harper.core.component.CPreview
import com.harper.core.component.CScaffold
import com.harper.core.component.CTabBarCommon
import com.harper.core.component.CToolbar
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme
import com.harper.core.ui.ComponentViewModel
import timber.log.Timber

private const val COLUMNS_COUNT = 3

@Composable
@OptIn(ExperimentalPagerApi::class)
fun TransactionScreen(
    viewModel: ComponentViewModel<TransactionState, TransactionEvent>,
    onAccountsSelect: (Long, Long) -> Unit
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(state.accountSelection) {
        val sourceId = state.accountSelection?.sourceId
        val receiverId = state.accountSelection?.receiverId
        if (sourceId != null && receiverId != null) {
            onAccountsSelect.invoke(sourceId, receiverId)
        }
    }
    CScaffold(
        topBar = { TransactionTopBar(viewModel) },
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            val pagerState = rememberPagerState(initialPage = state.selectedPage)
            CTabBarCommon(
                data = state.tabBarData,
                pagerState = pagerState,
                onTabSelect = { viewModel.onEvent(TransactionEvent.TabSelect(it)) }
            )
            HorizontalPager(
                state = pagerState,
                count = state.pages.size
            ) { pageIndex ->
                val page = state.pages[pageIndex]
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = CapitalTheme.dimensions.side)
                ) {
                    val accountSelection = state.accountSelection.takeIf { it?.type == page.type }
                    page.sourceDataSet?.let {
                        PageBlock(
                            title = stringResource(id = R.string.from),
                            accountsDataSet = it,
                            selectedAccountId = accountSelection?.sourceId,
                            isEnabled = { accountId -> accountId != accountSelection?.receiverId },
                            onAccountClick = { accountId ->
                                viewModel.onEvent(TransactionEvent.SourceAccountSelect(page.type, accountId))
                            },
                            onAddNewAccountClick = { accountType ->
                                viewModel.onEvent(TransactionEvent.NewAccountClick(page.type, accountType))
                            }
                        )
                    }
                    page.receiverDataSet?.let {
                        PageBlock(
                            title = stringResource(id = R.string.to),
                            accountsDataSet = it,
                            selectedAccountId = accountSelection?.receiverId,
                            isEnabled = { accountId -> accountId != accountSelection?.sourceId },
                            onAccountClick = { accountId ->
                                viewModel.onEvent(TransactionEvent.ReceiverAccountSelect(page.type, accountId))
                            },
                            onAddNewAccountClick = { accountType ->
                                viewModel.onEvent(TransactionEvent.NewAccountClick(page.type, accountType))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PageBlock(
    accountsDataSet: AccountDataSet,
    title: String,
    selectedAccountId: Long?,
    isEnabled: (Long) -> Boolean,
    modifier: Modifier = Modifier,
    onAccountClick: (Long) -> Unit,
    onAddNewAccountClick: (AccountType) -> Unit
) {
    val isEnabledCallback = remember { isEnabled }
    Column(modifier = modifier) {
        CHorizontalSpacer(height = CapitalTheme.dimensions.large)
        Text(text = title, style = CapitalTheme.typography.header)
        CHorizontalSpacer(height = CapitalTheme.dimensions.side)
        LazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(),
            columns = GridCells.Fixed(COLUMNS_COUNT),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalArrangement = Arrangement.spacedBy(CapitalTheme.dimensions.side),
            userScrollEnabled = false
        ) {
            items(accountsDataSet.accounts) { account ->
                AssetSource(
                    account = account,
                    isEnabled = isEnabledCallback.invoke(account.id),
                    isSelected = account.id == selectedAccountId,
                    onClick = { onAccountClick.invoke(account.id) },
                    onDrag = { x, y -> Timber.d("Drag ${account.name}: x=$x y=$y") }
                )
            }
            item {
                NewSource { onAddNewAccountClick.invoke(accountsDataSet.type) }
            }
        }
    }
}

@Composable
private fun TransactionTopBar(viewModel: ComponentViewModel<TransactionState, TransactionEvent>) {
    CToolbar(
        content = {
            Text(
                text = stringResource(id = R.string.new_transaction_title),
                style = CapitalTheme.typography.title,
                color = CapitalTheme.colors.onBackground
            )
        },
        navigation = {
            CIcon(imageVector = CapitalIcons.ArrowLeft, onClick = {
                viewModel.onEvent(TransactionEvent.BackClick)
            })
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun ContentLight() {
    TransactionScreen(TransactionMockViewModel()) { _, _ -> }
}

@Preview(showBackground = true)
@Composable
private fun ContentDark() {
    CPreview(isDark = true) {
        TransactionScreen(TransactionMockViewModel()) { _, _ -> }
    }
}
