package com.harper.capital.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

internal object LedgerTable {
    const val tableName = "ledgers"
    const val id = "id"
    const val transactionId = "transaction_id"
    const val accountId = "account_id"
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
            entity = AccountEntity::class,
            parentColumns = [AccountTable.id],
            childColumns = [LedgerTable.accountId],
            onDelete = ForeignKey.NO_ACTION
        )
    ],
    indices = [
        Index(value = [LedgerTable.transactionId, LedgerTable.accountId])
    ]
)
data class LedgerEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = LedgerTable.id) val id: Long = 0L,
    @ColumnInfo(name = LedgerTable.transactionId) val transactionId: Long,
    @ColumnInfo(name = LedgerTable.accountId) val accountId: Long,
    @ColumnInfo(name = LedgerTable.type) val type: LedgerEntityType,
    @ColumnInfo(name = LedgerTable.amount) val amount: Double
)
