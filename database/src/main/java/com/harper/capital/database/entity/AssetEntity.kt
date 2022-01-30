package com.harper.capital.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

internal object AssetTable {
    const val tableName = "assets"

    const val id = "id"
    const val name = "name"
    const val currencyId = "currency_id"
    const val type = "type"
    const val icon = "icon"
    const val color = "color"
    const val isIncluded = "is_included"
    const val isArchived = "is_archived"
}

@Entity(tableName = AssetTable.tableName)
data class AssetEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = AssetTable.id) val id: Long = 0L,
    @ColumnInfo(name = AssetTable.name) val name: String,
    @ColumnInfo(name = AssetTable.currencyId) val currencyId: Int,
    @ColumnInfo(name = AssetTable.type) val type: AssetEntityType,
    @ColumnInfo(name = AssetTable.color) val color: String,
    @ColumnInfo(name = AssetTable.icon) val icon: String,
    @ColumnInfo(name = AssetTable.isIncluded) val isIncluded: Boolean,
    @ColumnInfo(name = AssetTable.isArchived) val isArchived: Boolean
)
