package com.harper.capital.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

internal object TransactionTable {
    const val tableName = "transactions"
    const val id = "id"
    const val dateTime = "date_time"
    const val comment = "comment"
    const val isScheduled = "is_scheduled"
}

@Entity(tableName = TransactionTable.tableName)
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = TransactionTable.id) val id: Long = 0L,
    @ColumnInfo(name = TransactionTable.dateTime) val dateTime: LocalDateTime,
    @ColumnInfo(name = TransactionTable.comment) val comment: String? = null,
    @ColumnInfo(name = TransactionTable.isScheduled) val isScheduled: Boolean = false
)
