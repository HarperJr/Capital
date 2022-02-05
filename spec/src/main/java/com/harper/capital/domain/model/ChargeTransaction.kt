package com.harper.capital.domain.model

import java.time.LocalDateTime

data class ChargeTransaction(
    override val id: Long,
    val receiver: Account,
    override val amount: Double,
    override val dateTime: LocalDateTime,
    override val comment: String?,
    override val isScheduled: Boolean
) : Transaction(
    id = id,
    ledgers = listOf(Ledger(receiver, if (amount > 0) LedgerType.CREDIT else LedgerType.DEBIT)),
    amount = amount,
    dateTime = dateTime,
    comment = comment,
    isScheduled = isScheduled
)
