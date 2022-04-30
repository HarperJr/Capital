package com.harper.capital.database.view

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.Embedded
import com.harper.capital.database.entity.AccountEntity
import com.harper.capital.database.entity.AccountTable
import com.harper.capital.database.entity.LedgerTable

internal object AssetBalanceTable {
    const val tableName = "asset_balances"
    const val balance = "balance"
}

@DatabaseView(
    viewName = AssetBalanceTable.tableName,
    value = """
        SELECT *, SUM(
            CASE WHEN A.${AccountTable.type} = 'ASSET' THEN
                CASE WHEN L.${LedgerTable.type} = 'DEBIT' THEN L.${LedgerTable.amount} ELSE -L.${LedgerTable.amount} END
            ELSE 
                CASE WHEN L.${LedgerTable.type} = 'DEBIT' THEN -L.${LedgerTable.amount} ELSE L.${LedgerTable.amount} END
            END
        ) AS ${AssetBalanceTable.balance}
        FROM ${AccountTable.tableName} A
        LEFT JOIN ${LedgerTable.tableName} L ON
        A.${AccountTable.id} = L.${LedgerTable.accountId}
        WHERE A.${AccountTable.isArchived} = 0
        GROUP BY A.${AccountTable.id}
    """
)
data class AssetBalanceView(
    @Embedded
    val account: AccountEntity,
    @ColumnInfo(name = AssetBalanceTable.balance)
    val balance: Double?
)
