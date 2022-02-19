package com.harper.capital.history

import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.AccountColor
import com.harper.capital.domain.model.AccountIcon
import com.harper.capital.domain.model.AccountType
import com.harper.capital.domain.model.Currency
import com.harper.capital.domain.model.TransferTransaction
import com.harper.capital.history.model.HistoryListEvent
import com.harper.capital.history.model.HistoryListItem
import com.harper.capital.history.model.HistoryListState
import com.harper.core.ui.ComponentViewModelV1
import java.time.LocalDate
import java.time.LocalDateTime

private const val PREVIEW_TRANSACTIONS_COUNT = 10

class HistoryListMockViewModel : ComponentViewModelV1<HistoryListState, HistoryListEvent>(
    initialState = HistoryListState()
) {
    private val assetFrom = Account(
        id = 0L,
        name = "Tinkoff Black",
        type = AccountType.ASSET,
        balance = 1000.0,
        currency = Currency.RUB,
        color = AccountColor.TINKOFF,
        icon = AccountIcon.TINKOFF,
        metadata = null
    )
    private val assetTo = Account(
        id = 1L,
        name = "Products",
        type = AccountType.LIABILITY,
        balance = 100.0,
        currency = Currency.RUB,
        color = AccountColor.CATEGORY,
        icon = AccountIcon.PRODUCTS,
        metadata = null
    )

    init {
        update {
            it.copy(items = createTransactions(PREVIEW_TRANSACTIONS_COUNT))
        }
    }

    override fun onEvent(event: HistoryListEvent) {
        /**nope**/
    }

    private fun createTransactions(count: Int): List<HistoryListItem> {
        return (0..count).map {
            if (it in listOf(0, count / 2, count / 4)) {
                HistoryListItem.TransactionDateScopeItem(date = LocalDate.now(), amount = Math.random() * 10000, currency = Currency.RUB)
            } else {
                HistoryListItem.TransferTransactionItem(
                    TransferTransaction(
                        id = it.toLong(),
                        source = assetFrom,
                        receiver = assetTo,
                        amount = Math.random() * 100,
                        dateTime = LocalDateTime.now(),
                        comment = null,
                        isScheduled = false
                    )
                )
            }
        }
    }
}
