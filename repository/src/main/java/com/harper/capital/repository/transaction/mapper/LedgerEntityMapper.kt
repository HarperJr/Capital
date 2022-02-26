package com.harper.capital.repository.transaction.mapper

import com.harper.capital.database.entity.LedgerEntity
import com.harper.capital.domain.model.AccountType
import com.harper.capital.domain.model.Ledger
import com.harper.capital.domain.model.LedgerType

internal object LedgerEntityMapper : (Long, Double, List<Ledger>) -> List<LedgerEntity> {

    override fun invoke(transactionId: Long, amount: Double, models: List<Ledger>): List<LedgerEntity> = models.map {
        LedgerEntity(
            id = it.id,
            transactionId = transactionId,
            accountId = it.account.id,
            type = LedgerTypeEntityMapper(it.type),
            amount = if (it.account.type == AccountType.INCOME && it.type == LedgerType.CREDIT) 0.0 else amount
        )
    }
}
