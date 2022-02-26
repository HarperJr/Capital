package com.harper.capital.domain.model

data class Ledger(
    val id: Long,
    val account: Account,
    val type: LedgerType
)
