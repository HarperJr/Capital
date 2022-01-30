package com.harper.capital.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

internal object GoalTable {
    const val tableName = "goals"

    const val id = "id"
    const val assetId = "asset_id"
    const val goal = "goal"
}

@Entity(
    tableName = GoalTable.tableName,
    foreignKeys = [
        ForeignKey(
            entity = AssetEntity::class,
            childColumns = [GoalTable.assetId],
            parentColumns = [AssetTable.id],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class GoalEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = GoalTable.id) val id: Long = 0L,
    @ColumnInfo(name = GoalTable.assetId) val assetId: Long,
    @ColumnInfo(name = GoalTable.goal) val goal: Double
)
