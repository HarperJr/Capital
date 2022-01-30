package com.harper.capital.database.view

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.Embedded
import com.harper.capital.database.entity.AssetEntity
import com.harper.capital.database.entity.AssetTable
import com.harper.capital.database.entity.LedgerTable
import com.harper.capital.database.entity.TransactionTable

internal object AssetBalanceTable {
    const val tableName = "asset_balances"

    const val balance = "balance"
}

@DatabaseView(
    viewName = AssetBalanceTable.tableName,
    value = """
        SELECT *, SUM(CASE WHEN L.${LedgerTable.type} = 'CREDIT' THEN
        L.${LedgerTable.amount} ELSE -L.${LedgerTable.amount} END) AS ${AssetBalanceTable.balance}
        FROM ${AssetTable.tableName} A
        LEFT JOIN ${LedgerTable.tableName} L ON
        A.${AssetTable.id} = L.${LedgerTable.assetId}
        GROUP BY A.${AssetTable.id}
    """
)
data class AssetBalanceView(
    @Embedded
    val asset: AssetEntity,
    @ColumnInfo(name = AssetBalanceTable.balance)
    val balance: Double?
)
