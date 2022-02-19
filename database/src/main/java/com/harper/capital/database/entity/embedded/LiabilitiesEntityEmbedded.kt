package com.harper.capital.database.entity.embedded

import androidx.room.ColumnInfo

object LiabilitiesTable {
    const val accountId = "account_id"
    const val liabilities = "liabilities"
}

data class LiabilitiesEntityEmbedded(
    @ColumnInfo(name = LiabilitiesTable.accountId) val accountId: Long,
    @ColumnInfo(name = LiabilitiesTable.liabilities) val liabilities: Double?
)
