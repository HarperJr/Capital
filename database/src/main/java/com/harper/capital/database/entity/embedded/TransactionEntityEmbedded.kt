package com.harper.capital.database.entity.embedded

import androidx.room.Embedded
import androidx.room.Relation
import com.harper.capital.database.entity.AssetEntity
import com.harper.capital.database.entity.AssetTable
import com.harper.capital.database.entity.TransactionEntity
import com.harper.capital.database.entity.TransactionTable

data class TransactionEntityEmbedded(
    @Embedded
    val transaction: TransactionEntity,
    @Relation(
        parentColumn = TransactionTable.assetFromId,
        entityColumn = AssetTable.id
    )
    val assetFrom: AssetEntity,
    @Relation(
        parentColumn = TransactionTable.assetToId,
        entityColumn = AssetTable.id
    )
    val assetTo: AssetEntity
)
