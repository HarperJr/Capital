package com.harper.capital.history.model

import com.harper.capital.domain.model.Transaction

data class HistoryListState(
    val transactions: List<Transaction> = emptyList()
)
