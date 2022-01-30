package com.harper.capital.history

import com.harper.capital.domain.model.Asset
import com.harper.capital.domain.model.AssetColor
import com.harper.capital.domain.model.AssetIcon
import com.harper.capital.domain.model.AssetMetadata
import com.harper.capital.domain.model.Currency
import com.harper.capital.domain.model.Transaction
import com.harper.capital.domain.model.TransactionType
import com.harper.capital.history.model.HistoryListEvent
import com.harper.capital.history.model.HistoryListState
import com.harper.core.ui.ComponentViewModel
import com.harper.core.ui.EventObserver
import java.time.LocalDateTime

private const val PREVIEW_TRANSACTIONS_COUNT = 10

class HistoryListMockViewModel : ComponentViewModel<HistoryListState>(
    defaultState = HistoryListState()
), EventObserver<HistoryListEvent> {
    private val assetFrom = Asset(
        id = 0L,
        name = "Tinkoff Black",
        balance = 1000.0,
        currency = Currency.RUB,
        color = AssetColor.TINKOFF,
        icon = AssetIcon.TINKOFF,
        metadata = AssetMetadata.Debet
    )
    private val assetTo = Asset(
        id = 1L,
        name = "Products",
        balance = 100.0,
        currency = Currency.RUB,
        color = AssetColor.CATEGORY,
        icon = AssetIcon.PRODUCTS,
        metadata = AssetMetadata.Expense
    )

    init {
        mutateState {
            it.copy(transactions = createTransactions(PREVIEW_TRANSACTIONS_COUNT))
        }
    }

    override fun onEvent(event: HistoryListEvent) {
        /**nope**/
    }

    private fun createTransactions(count: Int): List<Transaction> {
        return (0..count).map {
            Transaction(
                id = it.toLong(),
                source = assetFrom,
                receiver = assetTo,
                amount = Math.random() * 100,
                dateTime = LocalDateTime.now(),
                comment = null,
                isScheduled = false
            )
        }
    }
}
