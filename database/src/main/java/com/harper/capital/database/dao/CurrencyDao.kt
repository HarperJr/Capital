package com.harper.capital.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.harper.capital.database.entity.CurrencyRateEntity
import com.harper.capital.database.entity.CurrencyRateTable
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entities: List<CurrencyRateEntity>)

    @Query("SELECT * FROM ${CurrencyRateTable.tableName}")
    fun selectAll(): Flow<List<CurrencyRateEntity>>

    @Query("SELECT * FROM ${CurrencyRateTable.tableName} WHERE ${CurrencyRateTable.name} = :name")
    suspend fun selectByName(name: String): CurrencyRateEntity
}
