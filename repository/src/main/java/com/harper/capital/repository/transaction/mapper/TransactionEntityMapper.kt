package com.harper.capital.repository.transaction.mapper

import com.harper.capital.database.entity.TransactionEntity
import com.harper.capital.domain.model.Transaction

internal object TransactionEntityMapper : (Transaction) -> TransactionEntity {

    override fun invoke(model: Transaction): TransactionEntity = with(model) {
        TransactionEntity(
            id = id,
            dateTime = dateTime,
            comment = comment,
            isScheduled = isScheduled
        )
    }
}
