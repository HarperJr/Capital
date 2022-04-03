package com.harper.capital.database.entity

import androidx.room.ColumnInfo
import java.time.LocalDateTime

internal object BalancePartitionTable {
    const val accountId = "account_id"
    const val period = "period"
    const val balance = "balance"
}

data class BalancePartitionEntity(
    @ColumnInfo(name = BalancePartitionTable.accountId) val accountId: Long,
    @ColumnInfo(name = BalancePartitionTable.period) val period: LocalDateTime,
    @ColumnInfo(name = BalancePartitionTable.balance) val balance: Double?
)
