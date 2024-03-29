package com.harper.capital.history.model

import com.harper.capital.domain.model.Currency
import java.time.LocalDate

data class HistoryListState(
    val liabilities: Double = 0.0,
    val currency: Currency = Currency.RUB,
    val items: List<HistoryListItem> = emptyList(),
    val dateStart: LocalDate = LocalDate.now(),
    val dateEnd: LocalDate = LocalDate.now(),
    val datePickerDialogState: DatePickerDialogState = DatePickerDialogState(isVisible = false)
)
