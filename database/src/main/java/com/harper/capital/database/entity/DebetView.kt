package com.harper.capital.database.entity

import androidx.room.ColumnInfo
import androidx.room.DatabaseView

object DebetViewTable {
    const val viewName = "debet_view"

    const val assetId = "asset_id"
    const val amount = "amount"
}

@DatabaseView(
    viewName = DebetViewTable.viewName,
    value = """
    SELECT ${TransactionTable.assetFromId} AS ${DebetViewTable.assetId},
    ${TransactionTable.amount} AS ${DebetViewTable.amount}
    FROM ${TransactionTable.tableName} 
    WHERE ${TransactionTable.type} = "EXPENSE"
    """
)
class DebetView(
    @ColumnInfo(name = DebetViewTable.assetId) val assetId: Long,
    @ColumnInfo(name = DebetViewTable.amount) val amount: Double
)
