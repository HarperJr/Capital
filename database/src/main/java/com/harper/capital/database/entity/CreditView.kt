package com.harper.capital.database.entity

import androidx.room.ColumnInfo
import androidx.room.DatabaseView

object CreditViewTable {
    const val viewName = "credit_view"

    const val assetId = "asset_id"
    const val amount = "amount"
}

@DatabaseView(
    viewName = CreditViewTable.viewName,
    value = """
    SELECT ${TransactionTable.assetToId} AS ${CreditViewTable.assetId}, ${TransactionTable.amount} AS ${CreditViewTable.amount}
    FROM ${TransactionTable.tableName} 
    WHERE ${TransactionTable.type} = "INCOME"
    """
)
data class CreditView(
    @ColumnInfo(name = CreditViewTable.assetId) val assetId: Long,
    @ColumnInfo(name = CreditViewTable.amount) val amount: Double
)
