package com.harper.capital.transaction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.harper.capital.R
import com.harper.capital.transaction.component.AssetSource
import com.harper.capital.transaction.component.NewSource
import com.harper.capital.transaction.model.DataSetSection
import com.harper.capital.transaction.model.TransactionEvent
import com.harper.capital.transaction.model.TransactionPage
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

@Composable
@OptIn(ExperimentalPagerApi::class)
fun TransactionScreen(viewModel: ComponentViewModel<TransactionState, TransactionEvent>) {
    val state by viewModel.state.collectAsState()
    CScaffold(
        topBar = { TransactionTopBar(viewModel) },
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
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
                PageBlock(page = state.pages[pageIndex], viewModel)
            }
        }
    }
}

@Composable
private fun PageBlock(page: TransactionPage, viewModel: ComponentViewModel<TransactionState, TransactionEvent>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        page.accountDataSets.forEach { (section, dataSet) ->
            CHorizontalSpacer(height = 24.dp)
            Text(text = section.resolveTitle(), style = CapitalTheme.typography.button)
            CHorizontalSpacer(height = 8.dp)
            FlowRow(
                mainAxisSpacing = 8.dp,
                crossAxisSpacing = 8.dp
            ) {
                dataSet.accounts.forEach { (account, isEnabled) ->
                    AssetSource(
                        account = account,
                        isEnabled = isEnabled,
                        isSelected = account.id == dataSet.selectedAccountId,
                        onSelect = { viewModel.onEvent(TransactionEvent.AssetSourceSelect(page.type, section, account)) },
                        onDrag = { x, y -> Timber.d("Drag ${account.name}: x=$x y=$y") })
                }
                NewSource { viewModel.onEvent(TransactionEvent.NewSourceClick(page.type, dataSet.type)) }
            }
        }
    }
}

@Composable
private fun DataSetSection.resolveTitle(): String = when (this) {
    DataSetSection.FROM -> stringResource(id = R.string.from)
    DataSetSection.TO -> stringResource(id = R.string.to)
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
    TransactionScreen(TransactionMockViewModel())
}

@Preview(showBackground = true)
@Composable
private fun ContentDark() {
    CPreview(isDark = true) {
        TransactionScreen(TransactionMockViewModel())
    }
}
