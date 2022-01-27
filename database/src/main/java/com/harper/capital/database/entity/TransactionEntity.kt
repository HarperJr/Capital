package com.harper.capital.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDateTime

object TransactionTable {
    const val tableName = "trans"

    const val id = "id"
    const val currencyId = "currency_id"
    const val amount = "amount"
    const val assetFromId = "asset_from_id"
    const val assetToId = "asset_to_id"
    const val type = "type"
    const val dateTime = "date_time"
    const val isScheduled = "is_scheduled"
    const val comment = "comment"
    const val isIncluded = "is_included"
}

@Entity(
    tableName = TransactionTable.tableName, foreignKeys = [
        ForeignKey(
            entity = AssetEntity::class,
            parentColumns = [AssetTable.id],
            childColumns = [TransactionTable.assetFromId],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = AssetEntity::class,
            parentColumns = [AssetTable.id],
            childColumns = [TransactionTable.assetToId],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = TransactionTable.id) val id: Long,
    @ColumnInfo(name = TransactionTable.currencyId) val currencyId: Int,
    @ColumnInfo(name = TransactionTable.amount) val amount: Double,
    @ColumnInfo(name = TransactionTable.assetFromId) val assetFromId: Long,
    @ColumnInfo(name = TransactionTable.assetToId) val assetToId: Long,
    @ColumnInfo(name = TransactionTable.type) val type: TransactionEntityType,
    @ColumnInfo(name = TransactionTable.dateTime) val dateTime: LocalDateTime,
    @ColumnInfo(name = TransactionTable.isScheduled) val isScheduled: Boolean,
    @ColumnInfo(name = TransactionTable.comment) val comment: String?,
    @ColumnInfo(name = TransactionTable.isIncluded) val isIncluded: Boolean
)
