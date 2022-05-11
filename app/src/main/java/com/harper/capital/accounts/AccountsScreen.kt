package com.harper.capital.accounts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import com.harper.capital.R
import com.harper.capital.accounts.model.AccountDataSet
import com.harper.capital.accounts.model.AccountsEvent
import com.harper.capital.accounts.model.AccountsState
import com.harper.capital.accounts.model.DataSetSection
import com.harper.capital.transaction.component.AssetSource
import com.harper.capital.transaction.component.NewSource
import com.harper.capital.transaction.model.TransactionEvent
import com.harper.core.component.CHorizontalSpacer
import com.harper.core.component.CScaffold
import com.harper.core.component.CToolbarCommon
import com.harper.core.theme.CapitalTheme
import com.harper.core.ui.ComponentViewModel
import timber.log.Timber

@Composable
fun AccountsScreen(viewModel: ComponentViewModel<AccountsState, AccountsEvent>) {
    val state by viewModel.state.collectAsState()
    CScaffold(topBar = { AccountsTopBar(viewModel) }) {
        LazyColumn {
            items(state.accountDataSets.toList()) { (section, dataSet) ->
                AccountSetItem(modifier = Modifier.fillParentMaxWidth(), viewModel, section, dataSet)
            }
        }
    }
}

@Composable
private fun AccountSetItem(
    modifier: Modifier = Modifier,
    viewModel: ComponentViewModel<AccountsState, AccountsEvent>,
    section: DataSetSection,
    dataSet: AccountDataSet
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = CapitalTheme.dimensions.side)
    ) {
        CHorizontalSpacer(height = CapitalTheme.dimensions.large)
        Text(text = section.resolveTitle(), style = CapitalTheme.typography.button)
        CHorizontalSpacer(height = CapitalTheme.dimensions.medium)
        FlowRow(
            mainAxisSpacing = 8.dp,
            crossAxisSpacing = 8.dp
        ) {
            dataSet.accounts.forEach { account ->
                AssetSource(
                    account = account,
                    isEnabled = true,
                    isSelected = false,
                    onSelect = { viewModel.onEvent(AccountsEvent.SourceClick(section, account.id)) },
                    onDrag = { x, y -> Timber.d("Drag ${account.name}: x=$x y=$y") })
            }
            NewSource { viewModel.onEvent(AccountsEvent.NewSourceClick(section)) }
        }
    }
}

@Composable
private fun DataSetSection.resolveTitle(): String = when (this) {
    DataSetSection.INCOME -> stringResource(id = R.string.incomes)
    DataSetSection.ASSET -> stringResource(id = R.string.assets)
    DataSetSection.LIABILITY -> stringResource(id = R.string.liabilities)
    DataSetSection.DEBT -> stringResource(id = R.string.debts)
}

@Composable
private fun AccountsTopBar(viewModel: ComponentViewModel<AccountsState, AccountsEvent>) {
    CToolbarCommon(
        title = stringResource(id = R.string.accounts),
        onNavigationClick = { viewModel.onEvent(AccountsEvent.BackClick) }
    )
}
