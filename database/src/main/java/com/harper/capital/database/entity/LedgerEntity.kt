package com.harper.capital.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

internal object LedgerTable {
    const val tableName = "ledgers"

    const val id = "id"
    const val transactionId = "transaction_id"
    const val assetId = "asset_id"
    const val type = "type"
    const val amount = "amount"
}

@Entity(
    tableName = LedgerTable.tableName,
    foreignKeys = [
        ForeignKey(
            entity = TransactionEntity::class,
            parentColumns = [TransactionTable.id],
            childColumns = [LedgerTable.transactionId],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = AssetEntity::class,
            parentColumns = [AssetTable.id],
            childColumns = [LedgerTable.assetId],
            onDelete = ForeignKey.NO_ACTION
        )
    ]
)
data class LedgerEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = LedgerTable.id) val id: Long = 0L,
    @ColumnInfo(name = LedgerTable.transactionId) val transactionId: Long,
    @ColumnInfo(name = LedgerTable.assetId) val assetId: Long,
    @ColumnInfo(name = LedgerTable.type) val type: LedgerEntityType,
    @ColumnInfo(name = LedgerTable.amount) val amount: Double
)
