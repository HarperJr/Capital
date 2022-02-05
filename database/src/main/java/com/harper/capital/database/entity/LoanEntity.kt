package com.harper.capital.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

internal object LoanTable {
    const val tableName = "credits"
    const val id = "id"
    const val accountId = "account_id"
    const val limit = "limit"
}

@Entity(
    tableName = LoanTable.tableName,
    foreignKeys = [
        ForeignKey(
            entity = AccountEntity::class,
            childColumns = [LoanTable.accountId],
            parentColumns = [AccountTable.id],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class LoanEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = LoanTable.id) val id: Long = 0L,
    @ColumnInfo(name = LoanTable.accountId) val accountId: Long,
    @ColumnInfo(name = LoanTable.limit) val limit: Double
)
