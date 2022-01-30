package com.harper.capital.repository.mapper

import com.harper.capital.database.entity.AssetEntityType
import com.harper.capital.database.entity.LedgerEntityType
import com.harper.capital.database.entity.embedded.LedgerEntityEmbedded
import com.harper.capital.database.entity.embedded.TransactionEntityEmbedded
import com.harper.capital.domain.model.Asset
import com.harper.capital.domain.model.Transaction

internal object TransactionMapper : (List<TransactionEntityEmbedded>) -> List<Transaction> {

    override fun invoke(entities: List<TransactionEntityEmbedded>): List<Transaction> =
        entities.map {
            val debet = it.ledgers.first { e -> e.ledger.type == LedgerEntityType.DEBET }
            val credit = it.ledgers.first { e -> e.ledger.type == LedgerEntityType.CREDIT }
            Transaction(
                id = it.transaction.id,
                source = mapLedger(debet),
                receiver = mapLedger(credit),
                amount = if (credit.asset.type == AssetEntityType.EXPENSE) -credit.ledger.amount else debet.ledger.amount,
                dateTime = it.transaction.dateTime,
                comment = it.transaction.comment,
                isScheduled = it.transaction.isScheduled
            )
        }

    private fun mapLedger(entity: LedgerEntityEmbedded): Asset = AssetMapper(entity.asset, null)
}
