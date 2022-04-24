package com.harper.capital.domain.model

import java.time.LocalDateTime

data class Transaction(
    val id: Long,
    val ledgers: List<Ledger>,
    val dateTime: LocalDateTime,
    val comment: String?,
    val isScheduled: Boolean
) {
    val isCharge: Boolean
        get() = ledgers.size == 1

    val source: Ledger
        get() = ledgers.first()
}
