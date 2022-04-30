package com.harper.capital.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

internal object CurrencyRateTable {
    const val tableName = "currency"
    const val id = "id"
    const val name = "name"
    const val rate = "rate"
}

@Entity(tableName = CurrencyRateTable.tableName)
data class CurrencyRateEntity(
    @PrimaryKey
    @ColumnInfo(name = CurrencyRateTable.id) val id: Long,
    @ColumnInfo(name = CurrencyRateTable.name) val name: String,
    @ColumnInfo(name = CurrencyRateTable.rate) val rate: Double
)
