package com.harper.capital.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

internal object CreditTable {
    const val tableName = "credits"

    const val id = "id"
    const val assetId = "asset_id"
    const val limit = "limit"
}

@Entity(
    tableName = CreditTable.tableName,
    foreignKeys = [
        ForeignKey(
            entity = AssetEntity::class,
            childColumns = [CreditTable.assetId],
            parentColumns = [AssetTable.id],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CreditEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = CreditTable.id) val id: Long = 0L,
    @ColumnInfo(name = CreditTable.assetId) val assetId: Long,
    @ColumnInfo(name = CreditTable.limit) val limit: Double
)
