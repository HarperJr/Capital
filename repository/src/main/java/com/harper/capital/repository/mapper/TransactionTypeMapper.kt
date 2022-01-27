package com.harper.capital.repository.mapper

import com.harper.capital.database.entity.TransactionEntityType
import com.harper.capital.domain.model.TransactionType

internal object TransactionTypeMapper : (TransactionEntityType) -> TransactionType {

    override fun invoke(entity: TransactionEntityType): TransactionType = when (entity) {
        TransactionEntityType.EXPENSE -> TransactionType.EXPENSE
        TransactionEntityType.INCOME -> TransactionType.INCOME
        TransactionEntityType.SEND -> TransactionType.SEND
        TransactionEntityType.DUTY -> TransactionType.DUTY
        TransactionEntityType.GOAL -> TransactionType.GOAL
    }
}
