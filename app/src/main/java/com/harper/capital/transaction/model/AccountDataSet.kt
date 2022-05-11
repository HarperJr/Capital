package com.harper.capital.transaction.model

import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.AccountType

data class AccountDataSet(
    val type: AccountType,
    val accounts: List<SelectableAccount>,
    val selectedAccountId: Long? = null
)

data class SelectableAccount(
    val account: Account,
    val isEnabled: Boolean = true
)
