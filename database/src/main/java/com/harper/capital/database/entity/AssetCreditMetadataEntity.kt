package com.harper.capital.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

object AssetCreditMetadataTable {
    const val tableName = "credit"

    const val id = "id"
    const val assetId = "asset_id"
    const val limit = "limit"
}

@Entity(
    tableName = AssetCreditMetadataTable.tableName,
    foreignKeys = [
        ForeignKey(
            entity = AssetEntity::class,
            childColumns = [AssetCreditMetadataTable.assetId],
            parentColumns = [AssetTable.id],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class AssetCreditMetadataEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = AssetCreditMetadataTable.id) val id: Long = 0L,
    @ColumnInfo(name = AssetCreditMetadataTable.assetId) val assetId: Long,
    @ColumnInfo(name = AssetCreditMetadataTable.limit) val limit: Double
)
