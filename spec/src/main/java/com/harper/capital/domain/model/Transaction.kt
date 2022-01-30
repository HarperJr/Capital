package com.harper.capital.domain.model

import java.time.LocalDateTime

class Transaction(
    val id: Long,
    val source: Asset,
    val receiver: Asset,
    val amount: Double,
    val dateTime: LocalDateTime,
    val comment: String?,
    val isScheduled: Boolean
)
