package com.harper.capital.transaction.model

import com.harper.capital.domain.model.Account

data class AccountDataSet(
    val type: DataSetType,
    val section: DataSetSection,
    val accounts: List<Account>,
    val selectedAccountId: Long? = null
)
