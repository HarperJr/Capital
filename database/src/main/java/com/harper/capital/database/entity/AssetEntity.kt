package com.harper.capital.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

object AssetTable {
    const val tableName = "asset"

    const val id = "id"
    const val name = "name"
    const val currencyId = "currency_id"
    const val type = "type"
    const val icon = "icon"
    const val color = "color"
}

@Entity(tableName = AssetTable.tableName)
data class AssetEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = AssetTable.id) val id: Long = 0L,
    @ColumnInfo(name = AssetTable.name) val name: String,
    @ColumnInfo(name = AssetTable.currencyId) val currencyId: Int,
    @ColumnInfo(name = AssetTable.type) val type: AssetEntityType,
    @ColumnInfo(name = AssetTable.color) val color: String,
    @ColumnInfo(name = AssetTable.icon) val icon: String
)
