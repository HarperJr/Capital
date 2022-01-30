package com.harper.capital.database.entity.embedded

import androidx.room.Embedded
import androidx.room.Relation
import com.harper.capital.database.entity.LedgerEntity
import com.harper.capital.database.entity.LedgerTable
import com.harper.capital.database.entity.TransactionEntity
import com.harper.capital.database.entity.TransactionTable

data class TransactionEntityEmbedded(
    @Embedded
    val transaction: TransactionEntity,
    @Relation(
        entity = LedgerEntity::class,
        parentColumn = TransactionTable.id,
        entityColumn = LedgerTable.transactionId
    )
    val ledgers: List<LedgerEntityEmbedded>
)