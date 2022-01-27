package com.harper.capital.repository.mapper

import com.harper.capital.database.entity.embedded.TransactionEntityEmbedded
import com.harper.capital.domain.model.Currency
import com.harper.capital.domain.model.Transaction

internal object TransactionMapper : (TransactionEntityEmbedded) -> Transaction {

    override fun invoke(entity: TransactionEntityEmbedded): Transaction = with(entity) {
        Transaction(
            id = transaction.id,
            type = TransactionTypeMapper(transaction.type),
            assetFrom = AssetMapper(assetFrom, metadata = null),
            assetTo = AssetMapper(assetTo, metadata = null),
            amount = transaction.amount,
            currency = Currency.of(transaction.currencyId),
            dateTime = transaction.dateTime,
            comment = transaction.comment,
            isScheduled = transaction.isScheduled,
            isIncluded = transaction.isIncluded
        )
    }
}