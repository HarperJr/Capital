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
        Ledger(source, LedgerType.CREDIT),
        Ledger(receiver, LedgerType.DEBIT)
    ),
    amount = amount,
    dateTime = dateTime,
    comment = comment,
    isScheduled = isScheduled
)
