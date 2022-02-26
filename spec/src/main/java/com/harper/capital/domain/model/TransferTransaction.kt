package com.harper.capital.domain.model

import java.time.LocalDateTime

data class TransferTransaction(
    override val id: Long,
    val source: Account,
    val receiver: Account,
    override val amount: Double,
    override val dateTime: LocalDateTime,
    override val comment: String?,
    override val isScheduled: Boolean
) : Transaction(
    id = id,
    ledgers = listOf(
        Ledger(id = 0L, source, LedgerType.CREDIT),
        Ledger(id = 0L, receiver, LedgerType.DEBIT)
    ),
    amount = amount,
    dateTime = dateTime,
    comment = comment,
    isScheduled = isScheduled
)
