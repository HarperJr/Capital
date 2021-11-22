package com.harper.capital.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.harper.capital.database.entity.AssetEntity
import com.harper.capital.database.entity.AssetTable

@Dao
interface AssetDao {

    @Insert
    suspend fun insert(entity: AssetEntity)

    @Delete
    suspend fun delete(entity: AssetEntity)

    @Query("SELECT * FROM ${AssetTable.tableName}")
    suspend fun selectAll(): List<AssetEntity>

    @Query("SELECT * FROM ${AssetTable.tableName} WHERE ${AssetTable.id} = :id")
    suspend fun selectById(id: Long): AssetEntity?
}