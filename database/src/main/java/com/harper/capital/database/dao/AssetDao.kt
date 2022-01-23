package com.harper.capital.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.harper.capital.database.entity.AssetEntity
import com.harper.capital.database.entity.AssetEntityType
import com.harper.capital.database.entity.AssetTable
import com.harper.capital.database.entity.CreditEntity
import com.harper.capital.database.entity.CreditTable
import com.harper.capital.database.entity.GoalEntity
import com.harper.capital.database.entity.GoalTable
import kotlinx.coroutines.flow.Flow

@Dao
interface AssetDao {

    @Insert
    suspend fun insert(entity: AssetEntity): Long

    @Insert
    suspend fun insertCredit(entity: CreditEntity)

    @Insert
    suspend fun insertGoal(entity: GoalEntity)

    @Delete
    suspend fun delete(entity: AssetEntity)

    @Query("SELECT * FROM ${AssetTable.tableName}")
    fun selectAll(): Flow<List<AssetEntity>>

    @Query("SELECT * FROM ${AssetTable.tableName} WHERE ${AssetTable.type} IN (:types)")
    fun selectByTypes(types: List<AssetEntityType>): Flow<List<AssetEntity>>

    @Query("SELECT * FROM ${CreditTable.tableName} WHERE ${CreditTable.assetId} = :assetId")
    suspend fun selectCreditByAssetId(assetId: Long): CreditEntity

    @Query("SELECT * FROM ${GoalTable.tableName} WHERE ${GoalTable.assetId} = :assetId")
    suspend fun selectGoalByAssetId(assetId: Long): GoalEntity

    @Query("SELECT * FROM ${AssetTable.tableName} WHERE ${AssetTable.id} = :id")
    suspend fun selectById(id: Long): AssetEntity
}