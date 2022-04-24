package com.harper.capital.history.model

import com.harper.capital.domain.model.Currency
import com.harper.capital.domain.model.Transaction
import java.time.LocalDate

class HistoryListItem(
    val date: LocalDate,
    val summaryAmount: Double,
    val currency: Currency,
    val transactions: List<Transaction>
)
