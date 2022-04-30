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
import com.harper.core.ui.ComponentViewModel
import java.time.LocalDate
import java.time.LocalDateTime

class HistoryListMockViewModel : ComponentViewModel<HistoryListState, HistoryListEvent>(
    initialState = HistoryListState()
) {
    private val source = Account(
        id = 0L,
        name = "Tinkoff Black",
        type = AccountType.ASSET,
        balance = 1000.0,
        currency = Currency.RUB,
        color = AccountColor.TINKOFF,
        icon = AccountIcon.TINKOFF,
        metadata = null
    )
    private val receiver = Account(
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
            it.copy(items = createTransactions())
        }
    }

    override fun onEvent(event: HistoryListEvent) {
        /**nope**/
    }

    private fun createTransactions(): List<HistoryListItem> {
        return (0 until 3).map {
            val amounts = (0 until 5).map { Math.random() * 10000 }
            HistoryListItem(
                date = LocalDate.now(),
                summaryAmount = amounts.sum(),
                currency = Currency.RUB,
                transactions = (0 until 5).map {
                    TransferTransaction(
                        source = source,
                        receiver = receiver,
                        sourceAmount = amounts[it],
                        receiverAmount = amounts[it],
                        dateTime = LocalDateTime.now(),
                        comment = null,
                        isScheduled = false
                    )
                }
            )
        }
    }
}
