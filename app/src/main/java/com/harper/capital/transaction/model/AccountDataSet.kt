package com.harper.capital.transaction.model

import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.AccountType

data class AccountDataSet(val type: AccountType, val accounts: List<Account>)
