package com.harper.capital.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

object AssetGoalMetadataTable {
    const val tableName = "goal"

    const val id = "id"
    const val assetId = "asset_id"
    const val goal = "goal"
}

@Entity(
    tableName = AssetGoalMetadataTable.tableName,
    foreignKeys = [
        ForeignKey(
            entity = AssetEntity::class,
            childColumns = [AssetGoalMetadataTable.assetId],
            parentColumns = [AssetTable.id],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class AssetGoalMetadataEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = AssetGoalMetadataTable.id) val id: Long = 0L,
    @ColumnInfo(name = AssetGoalMetadataTable.assetId) val assetId: Long,
    @ColumnInfo(name = AssetGoalMetadataTable.goal) val goal: Double
)
