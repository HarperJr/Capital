package com.harper.capital.repository.transaction.mapper

import com.harper.capital.database.entity.LedgerEntity
import com.harper.capital.domain.model.Ledger

internal object LedgerEntityMapper : (Long, Double, List<Ledger>) -> List<LedgerEntity> {

    override fun invoke(transactionId: Long, amount: Double, models: List<Ledger>): List<LedgerEntity> = models.map {
        LedgerEntity(
            transactionId = transactionId,
            accountId = it.account.id,
            type = LedgerTypeEntityMapper(it.type),
            amount = amount
        )
    }
}
