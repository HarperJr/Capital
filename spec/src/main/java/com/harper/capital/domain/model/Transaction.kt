package com.harper.capital.domain.model

import java.time.LocalDateTime

class Transaction(
    val id: Long,
    val type: TransactionType,
    val assetFrom: Asset,
    val assetTo: Asset,
    val amount: Double,
    val currency: Currency,
    val dateTime: LocalDateTime,
    val comment: String?,
    val isScheduled: Boolean,
    val isIncluded: Boolean
)
