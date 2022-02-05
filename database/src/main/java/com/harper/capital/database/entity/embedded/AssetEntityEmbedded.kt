package com.harper.capital.database.entity.embedded

import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.harper.capital.database.entity.AccountEntity
import com.harper.capital.database.view.AssetBalanceTable

data class AssetEntityEmbedded(
    @Embedded
    val account: AccountEntity,
    @ColumnInfo(name = AssetBalanceTable.balance)
    val balance: Double?,
)