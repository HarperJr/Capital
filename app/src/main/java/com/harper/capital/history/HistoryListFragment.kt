package com.harper.capital.history

import android.os.Parcelable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.harper.capital.R
import com.harper.capital.history.comonent.ChargeTransactionItem
import com.harper.capital.history.comonent.TransactionDateScopeItem
import com.harper.capital.history.comonent.TransactionItem
import com.harper.capital.history.model.HistoryListEvent
import com.harper.capital.history.model.HistoryListItem
import com.harper.capital.history.model.HistoryListState
import com.harper.capital.ui.base.ScreenLayout
import com.harper.core.component.CPreview
import com.harper.core.component.CScaffold
import com.harper.core.component.CToolbarCommon
import com.harper.core.component.Menu
import com.harper.core.component.MenuItem
import com.harper.core.theme.CapitalIcons
import com.harper.core.ui.ComponentFragment
import com.harper.core.ui.ComponentViewModel
import com.harper.core.ui.EventSender
import com.harper.core.ui.MockEventSender
import com.harper.core.ui.withArgs
import kotlinx.parcelize.Parcelize
import org.koin.core.parameter.parametersOf

private const val FILTER_MENU_ITEM_ID = 0

class HistoryListFragment : ComponentFragment<HistoryListViewModel>(),
    EventSender<HistoryListEvent> {
    override val viewModel: HistoryListViewModel by injectViewModel { parametersOf(params) }
    private val params by requireArg<Params>(PARAMS)

    override fun content(): @Composable () -> Unit = {
        ScreenLayout {
            HistoryListScreen(viewModel, this)
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
private fun HistoryListScreen(
    viewModel: ComponentViewModel<HistoryListState>,
    es: EventSender<HistoryListEvent>
) {
    val state by viewModel.state.collectAsState()

    CScaffold(topBar = { HistoryListTopBar(es) }) {
        Column {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(state.items) {
                    when (it) {
                        is HistoryListItem.TransferTransactionItem -> TransactionItem(transaction = it.transaction)
                        is HistoryListItem.ChargeTransactionItem -> ChargeTransactionItem(transaction = it.transaction)
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
private fun HistoryListTopBar(es: EventSender<HistoryListEvent>) {
    CToolbarCommon(
        title = stringResource(id = R.string.history_title),
        onNavigationClick = { es.send(HistoryListEvent.BackClick) },
        menu = Menu(listOf(MenuItem(id = FILTER_MENU_ITEM_ID, CapitalIcons.Filter)))
    ) { itemId ->
        if (itemId == FILTER_MENU_ITEM_ID) {
            es.send(HistoryListEvent.FilterItemClick)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HistoryListScreenLight() {
    CPreview {
        HistoryListScreen(viewModel = HistoryListMockViewModel(), es = MockEventSender())
    }
}

@Preview(showBackground = true)
@Composable
private fun HistoryListScreenDark() {
    CPreview(isDark = true) {
        HistoryListScreen(viewModel = HistoryListMockViewModel(), es = MockEventSender())
    }
}
