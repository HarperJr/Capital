package com.harper.capital.transaction.manage.model

import com.harper.capital.domain.model.TransferTransaction

private const val IDENTITY_EXCHANGE_RATE = 1.0

data class TransactionManageState(
    val mode: TransactionManageMode,
    val isLoading: Boolean = true,
    val transaction: TransferTransaction? = null,
    val exchangeRate: Double = 1.0
) {
    val hasExchange: Boolean
        get() = exchangeRate != IDENTITY_EXCHANGE_RATE
}
