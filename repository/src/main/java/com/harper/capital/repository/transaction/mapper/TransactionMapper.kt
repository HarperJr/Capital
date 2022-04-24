package com.harper.capital.repository.transaction.mapper

import com.harper.capital.database.entity.embedded.TransactionEntityEmbedded
import com.harper.capital.domain.model.Transaction

internal object TransactionMapper : (TransactionEntityEmbedded) -> Transaction {

    override fun invoke(entity: TransactionEntityEmbedded): Transaction =
        with(entity) {
            Transaction(
                id = transaction.id,
                ledgers = ledgers.map(LedgerMapper),
                dateTime = transaction.dateTime,
                comment = transaction.comment,
                isScheduled = transaction.isScheduled
            )
        }
}
