package com.harper.capital.transaction.manage.model

import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.Currency
import java.time.LocalDateTime

data class TransactionManageState(
    val mode: TransactionManageMode,
    val accounts: List<Account> = emptyList(),
    val exchangeState: ExchangeState = ExchangeState(),
    val date: LocalDateTime = LocalDateTime.now(),
    val comment: String? = null,
    val isScheduled: Boolean = false,
    val isLoading: Boolean = true
)

data class ExchangeState(
    val sourceCurrency: Currency = Currency.RUB,
    val receiverCurrency: Currency = Currency.RUB,
    val sourceAmount: Double = 0.0,
    val receiverAmount: Double = 0.0,
    val rate: Double = 1.0
) {
    val hasExchange: Boolean
        get() = sourceCurrency != receiverCurrency
}
