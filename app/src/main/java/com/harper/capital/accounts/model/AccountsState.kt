package com.harper.capital.accounts.model

data class AccountsState(
    val accountDataSets: Map<DataSetSection, AccountDataSet> = emptyMap()
)
