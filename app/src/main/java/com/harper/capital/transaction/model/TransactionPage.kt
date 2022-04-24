package com.harper.capital.transaction.model

data class TransactionPage(
    val type: TransactionType,
    val accountDataSets: Map<DataSetSection, AccountDataSet>
)
