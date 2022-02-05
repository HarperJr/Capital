package com.harper.capital.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

internal object GoalTable {
    const val tableName = "goals"
    const val id = "id"
    const val accountId = "account_id"
    const val goal = "goal"
}

@Entity(
    tableName = GoalTable.tableName,
    foreignKeys = [
        ForeignKey(
            entity = AccountEntity::class,
            childColumns = [GoalTable.accountId],
            parentColumns = [AccountTable.id],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class GoalEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = GoalTable.id) val id: Long = 0L,
    @ColumnInfo(name = GoalTable.accountId) val accountId: Long,
    @ColumnInfo(name = GoalTable.goal) val goal: Double
)
