package com.harper.capital.repository.mapper

import com.harper.capital.database.entity.TransactionEntity
import com.harper.capital.domain.model.Transaction

internal object TransactionEntityMapper : (Transaction) -> TransactionEntity {

    override fun invoke(model: Transaction): TransactionEntity = with(model) {
        TransactionEntity(
            id = id,
            currencyId = currency.ordinal,
            amount = amount,
            assetFromId = assetFrom.id,
            assetToId = assetTo.id,
            type = TransactionEntityTypeMapper(type),
            dateTime = dateTime,
            isScheduled = isScheduled,
            comment = comment,
            isIncluded = isIncluded
        )
    }
}
