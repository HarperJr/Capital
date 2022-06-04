package com.harper.capital.accounts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.harper.capital.R
import com.harper.capital.accounts.model.AccountDataSet
import com.harper.capital.accounts.model.AccountsEvent
import com.harper.capital.accounts.model.AccountsState
import com.harper.capital.accounts.model.DataSetSection
import com.harper.capital.transaction.component.AssetSource
import com.harper.capital.transaction.component.NewSource
import com.harper.core.component.CHorizontalSpacer
import com.harper.core.component.CScaffold
import com.harper.core.component.CToolbarCommon
import com.harper.core.theme.CapitalTheme
import com.harper.core.ui.ComponentViewModel
import timber.log.Timber

private const val COLUMNS_COUNT = 3

@Composable
fun AccountsScreen(viewModel: ComponentViewModel<AccountsState, AccountsEvent>) {
    val state by viewModel.state.collectAsState()
    CScaffold(topBar = { AccountsTopBar(viewModel) }) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            state.accountDataSets.forEach { (section, dataSet) ->
                AccountSetItem(
                    modifier = Modifier.fillMaxWidth(),
                    viewModel = viewModel,
                    section = section,
                    dataSet = dataSet
                )
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
    Column(modifier = modifier.padding(horizontal = CapitalTheme.dimensions.side)) {
        CHorizontalSpacer(height = CapitalTheme.dimensions.large)
        Text(text = section.resolveTitle(), style = CapitalTheme.typography.header)
        CHorizontalSpacer(height = CapitalTheme.dimensions.side)
        LazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(),
            columns = GridCells.Fixed(COLUMNS_COUNT),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalArrangement = Arrangement.spacedBy(CapitalTheme.dimensions.side),
            userScrollEnabled = false
        ) {
            items(dataSet.accounts) {
                AssetSource(
                    account = it,
                    isEnabled = true,
                    isSelected = false,
                    onClick = { viewModel.onEvent(AccountsEvent.SourceClick(section, it.id)) },
                    onDrag = { x, y -> Timber.d("Drag ${it.name}: x=$x y=$y") }
                )
            }
            item {
                NewSource { viewModel.onEvent(AccountsEvent.NewSourceClick(section)) }
            }
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

@Preview
@Composable
private fun AccountsScreenLight() {
    CapitalTheme(isDark = false) {
        AccountsScreen(viewModel = AccountsMockViewModel())
    }
}
