package com.harper.capital.database.entity.embedded

import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.harper.capital.database.entity.AssetEntity

data class AssetEntityEmbedded(
    @Embedded
    val asset: AssetEntity,
    @ColumnInfo(name = "credit")
    val credit: Double?,
    @ColumnInfo(name = "debet")
    val debet: Double?
)