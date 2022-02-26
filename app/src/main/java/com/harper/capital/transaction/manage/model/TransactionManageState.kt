package com.harper.capital.transaction.manage.model

import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.Currency
import java.time.LocalDate
import java.time.LocalDateTime

typealias AssetPair = Pair<Account, Account>

data class TransactionManageState(
    val mode: TransactionManageMode,
    val accountPair: AssetPair? = null,
    val amount: Double = 0.0,
    val currency: Currency = Currency.RUB,
    val date: LocalDateTime = LocalDateTime.now(),
    val comment: String? = null,
    val isScheduled: Boolean = false,
    val isLoading: Boolean = true
)
