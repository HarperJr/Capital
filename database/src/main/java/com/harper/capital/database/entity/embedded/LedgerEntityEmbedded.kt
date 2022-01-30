package com.harper.capital.database.entity.embedded

import androidx.room.Embedded
import androidx.room.Relation
import com.harper.capital.database.entity.AssetEntity
import com.harper.capital.database.entity.AssetTable
import com.harper.capital.database.entity.LedgerEntity
import com.harper.capital.database.entity.LedgerTable

data class LedgerEntityEmbedded(
    @Embedded
    val ledger: LedgerEntity,
    @Relation(
        parentColumn = LedgerTable.assetId,
        entityColumn = AssetTable.id
    )
    val asset: AssetEntity
)
