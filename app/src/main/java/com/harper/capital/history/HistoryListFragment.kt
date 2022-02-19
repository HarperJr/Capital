package com.harper.capital.history

import android.os.Parcelable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harper.capital.R
import com.harper.capital.history.comonent.ChargeTransactionItem
import com.harper.capital.history.comonent.TransactionDateScopeItem
import com.harper.capital.history.comonent.TransferTransactionItem
import com.harper.capital.history.model.HistoryListEvent
import com.harper.capital.history.model.HistoryListItem
import com.harper.capital.history.model.HistoryListState
import com.harper.capital.ui.base.ScreenLayout
import com.harper.core.component.CDatePickerDialog
import com.harper.core.component.CHorizontalSpacer
import com.harper.core.component.CPreview
import com.harper.core.component.CScaffold
import com.harper.core.component.CToolbarCommon
import com.harper.core.component.CVerticalSpacer
import com.harper.core.component.Menu
import com.harper.core.component.MenuItem
import com.harper.core.ext.formatAmount
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme
import com.harper.core.ui.ComponentFragmentV1
import com.harper.core.ui.ComponentViewModelV1
import com.harper.core.ui.withArgs
import kotlinx.parcelize.Parcelize
import org.koin.core.parameter.parametersOf
import java.time.format.DateTimeFormatter

private const val FILTER_MENU_ITEM_ID = 0
private val MMMMDateFormatter = DateTimeFormatter.ofPattern("MMMM")

class HistoryListFragment : ComponentFragmentV1<HistoryListViewModel>() {
    override val viewModel: HistoryListViewModel by injectViewModel { parametersOf(params) }
    private val params by requireArg<Params>(PARAMS)

    @Composable
    override fun ScreenContent() {
        ScreenLayout {
            HistoryListScreen(viewModel)
        }
    }

    @Parcelize
    class Params(val assetId: Long?) : Parcelable

    companion object {
        private const val PARAMS = "history_asset_params"

        fun newInstance(params: Params): HistoryListFragment =
            HistoryListFragment().withArgs(PARAMS to params)
    }
}

@Composable
private fun HistoryListScreen(viewModel: ComponentViewModelV1<HistoryListState, HistoryListEvent>) {
    val state by viewModel.state.collectAsState()

    CScaffold(topBar = { HistoryListTopBar(viewModel) }) {
        if (state.datePickerDialogState.isVisible) {
            CDatePickerDialog(
                date = state.datePickerDialogState.date,
                onDismiss = {
                    viewModel.onEvent(HistoryListEvent.HideDialog)
                },
                onDateSelect = { viewModel.onEvent(HistoryListEvent.MonthSelect(it)) }
            )
        }
        Column {
            CHorizontalSpacer(height = CapitalTheme.dimensions.medium)
            Card(
                modifier = Modifier.padding(horizontal = CapitalTheme.dimensions.side),
                backgroundColor = CapitalTheme.colors.background,
                shape = CapitalTheme.shapes.large,
                elevation = 0.dp,
                border = BorderStroke(width = 1.dp, color = CapitalTheme.colors.primaryVariant)
            ) {
                Column(modifier = Modifier.padding(CapitalTheme.dimensions.medium)) {
                    Text(
                        text = stringResource(id = R.string.summary_loss, state.liabilities.formatAmount(state.currency.name)),
                        style = CapitalTheme.typography.subtitle
                    )
                    CHorizontalSpacer(height = CapitalTheme.dimensions.medium)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(height = CapitalTheme.dimensions.side)
                            .background(color = CapitalTheme.colors.secondary, shape = CircleShape)
                    )
                }
            }
            CHorizontalSpacer(height = CapitalTheme.dimensions.large)
            Row(modifier = Modifier
                .clickable { viewModel.onEvent(HistoryListEvent.PeriodSelectorClick) }
                .padding(horizontal = CapitalTheme.dimensions.side), verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = CapitalIcons.Calendar, contentDescription = null, tint = CapitalTheme.colors.secondary)
                CVerticalSpacer(width = CapitalTheme.dimensions.medium)
                Text(
                    text = state.selectedMonth.format(MMMMDateFormatter),
                    color = CapitalTheme.colors.secondary,
                    style = CapitalTheme.typography.subtitle
                )
            }
            CHorizontalSpacer(height = CapitalTheme.dimensions.medium)
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(state.items) {
                    when (it) {
                        is HistoryListItem.ChargeTransactionItem -> ChargeTransactionItem(transaction = it.transaction)
                        is HistoryListItem.TransferTransactionItem -> TransferTransactionItem(transaction = it.transaction) {
                            viewModel.onEvent(HistoryListEvent.OnTransactionClick(it.transaction))
                        }
                        is HistoryListItem.TransactionDateScopeItem -> TransactionDateScopeItem(
                            date = it.date,
                            amount = it.amount,
                            currency = it.currency
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HistoryListTopBar(viewModel: ComponentViewModelV1<HistoryListState, HistoryListEvent>) {
    CToolbarCommon(
        title = stringResource(id = R.string.history_title),
        onNavigationClick = { viewModel.onEvent(HistoryListEvent.BackClick) },
        menu = Menu(listOf(MenuItem(id = FILTER_MENU_ITEM_ID, CapitalIcons.Filter)))
    ) { itemId ->
        if (itemId == FILTER_MENU_ITEM_ID) {
            viewModel.onEvent(HistoryListEvent.FilterItemClick)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HistoryListScreenLight() {
    CPreview {
        HistoryListScreen(viewModel = HistoryListMockViewModel())
    }
}

@Preview(showBackground = true)
@Composable
private fun HistoryListScreenDark() {
    CPreview(isDark = true) {
        HistoryListScreen(viewModel = HistoryListMockViewModel())
    }
}
