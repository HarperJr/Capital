package com.harper.capital.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

internal object AccountTable {
    const val tableName = "accounts"
    const val id = "id"
    const val name = "name"
    const val type = "type"
    const val color = "color"
    const val icon = "icon"
    const val currency = "currency"
    const val metadataType = "metadata_type"
    const val isIncluded = "is_included"
    const val isArchived = "is_archived"
}

@Entity(tableName = AccountTable.tableName)
data class AccountEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = AccountTable.id) val id: Long = 0L,
    @ColumnInfo(name = AccountTable.name) val name: String,
    @ColumnInfo(name = AccountTable.type) val type: AccountEntityType,
    @ColumnInfo(name = AccountTable.color) val color: String,
    @ColumnInfo(name = AccountTable.icon) val icon: String,
    @ColumnInfo(name = AccountTable.currency) val currency: String,
    @ColumnInfo(name = AccountTable.metadataType) val metadataType: AccountEntityMetadataType,
    @ColumnInfo(name = AccountTable.isIncluded) val isIncluded: Boolean,
    @ColumnInfo(name = AccountTable.isArchived) val isArchived: Boolean
)
