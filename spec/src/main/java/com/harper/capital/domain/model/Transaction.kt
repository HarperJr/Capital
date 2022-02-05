package com.harper.capital.domain.model

import java.time.LocalDateTime

open class Transaction(
    open val id: Long,
    val ledgers: List<Ledger>,
    open val amount: Double,
    open val dateTime: LocalDateTime,
    open val comment: String?,
    open val isScheduled: Boolean
)
