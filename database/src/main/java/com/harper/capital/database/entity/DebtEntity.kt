package com.harper.capital.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

internal object DebtTable {
    const val tableName = "debts"
    const val id = "id"
    const val accountId = "account_id"
    const val avatar = "avatar"
    const val phone = "phone"
}

@Entity(
    tableName = DebtTable.tableName,
    foreignKeys = [
        ForeignKey(
            entity = AccountEntity::class,
            childColumns = [DebtTable.accountId],
            parentColumns = [AccountTable.id],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DebtEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DebtTable.id) val id: Long = 0L,
    @ColumnInfo(name = DebtTable.accountId) val accountId: Long,
    @ColumnInfo(name = DebtTable.avatar) val avatar: String?,
    @ColumnInfo(name = DebtTable.phone) val phone: String?
)
