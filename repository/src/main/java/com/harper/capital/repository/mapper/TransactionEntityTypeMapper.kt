package com.harper.capital.repository.mapper

import com.harper.capital.database.entity.TransactionEntityType
import com.harper.capital.domain.model.TransactionType

internal object TransactionEntityTypeMapper : (TransactionType) -> TransactionEntityType {

    override fun invoke(model: TransactionType): TransactionEntityType = when (model) {
        TransactionType.INCOME -> TransactionEntityType.INCOME
        TransactionType.EXPENSE -> TransactionEntityType.EXPENSE
        TransactionType.SEND -> TransactionEntityType.SEND
        TransactionType.DUTY -> TransactionEntityType.DUTY
        TransactionType.GOAL -> TransactionEntityType.GOAL
    }
}
