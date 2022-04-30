package com.harper.capital.domain.model

data class Ledger(
    val id: Long = 0L,
    val account: Account,
    val type: LedgerType,
    val amount: Double
)
