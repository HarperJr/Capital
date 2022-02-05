package com.harper.capital.transaction.model

import com.harper.capital.domain.model.TransactionType

data class TransactionPage(
    val type: TransactionType,
    val accountDataSets: List<AccountDataSet>
)
