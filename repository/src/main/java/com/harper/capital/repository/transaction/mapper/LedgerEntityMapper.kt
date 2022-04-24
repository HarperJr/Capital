package com.harper.capital.repository.transaction.mapper

import com.harper.capital.database.entity.LedgerEntity
import com.harper.capital.domain.model.AccountType
import com.harper.capital.domain.model.Ledger
import com.harper.capital.domain.model.LedgerType

internal object LedgerEntityMapper : (Long, List<Ledger>) -> List<LedgerEntity> {

    override fun invoke(transactionId: Long, models: List<Ledger>): List<LedgerEntity> = models.map {
        LedgerEntity(
            id = it.id,
            transactionId = transactionId,
            accountId = it.account.id,
            type = LedgerTypeEntityMapper(it.type),
            amount = it.amount
        )
    }
}
