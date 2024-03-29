package com.harper.capital.history.model

import com.harper.capital.domain.model.Transaction
import java.time.LocalDate

sealed class HistoryListEvent {

    class OnTransactionClick(val transaction: Transaction) : HistoryListEvent()

    class DateRangeSelect(val dateStart: LocalDate?, val dateEnd: LocalDate?) : HistoryListEvent()

    object BackClick : HistoryListEvent()

    object FilterItemClick : HistoryListEvent()

    object PeriodSelectorClick : HistoryListEvent()

    object HideDialog : HistoryListEvent()
}
