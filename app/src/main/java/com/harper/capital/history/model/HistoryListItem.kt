package com.harper.capital.history.model

import com.harper.capital.domain.model.ChargeTransaction
import com.harper.capital.domain.model.Currency
import com.harper.capital.domain.model.TransferTransaction
import java.time.LocalDate

sealed class HistoryListItem {

    data class TransferTransactionItem(
        val transaction: TransferTransaction
    ) : HistoryListItem()

    data class ChargeTransactionItem(
        val transaction: ChargeTransaction
    ) : HistoryListItem()

    data class TransactionDateScopeItem(
        val date: LocalDate,
        val amount: Double,
        val currency: Currency
    ) : HistoryListItem()
}
