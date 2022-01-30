package com.harper.capital.database.entity.embedded

import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.harper.capital.database.entity.AssetEntity
import com.harper.capital.database.view.AssetBalanceTable

data class AssetEntityEmbedded(
    @Embedded
    val asset: AssetEntity,
    @ColumnInfo(name = AssetBalanceTable.balance)
    val balance: Double?,
)