package com.harper.capital.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.harper.capital.database.entity.AssetEntity
import com.harper.capital.database.entity.AssetEntityType
import com.harper.capital.database.entity.AssetTable
import com.harper.capital.database.entity.CreditEntity
import com.harper.capital.database.entity.CreditTable
import com.harper.capital.database.entity.GoalEntity
import com.harper.capital.database.entity.GoalTable
import com.harper.capital.database.entity.embedded.AssetEntityEmbedded
import com.harper.capital.database.view.AssetBalanceTable
import kotlinx.coroutines.flow.Flow

@Dao
interface AssetDao {

    @Insert
    suspend fun insert(entity: AssetEntity): Long

    @Insert
    suspend fun insertCredit(entity: CreditEntity)

    @Insert
    suspend fun insertGoal(entity: GoalEntity)

    @Update
    fun update(entity: AssetEntity)

    @Transaction
    @Query("SELECT * FROM ${AssetBalanceTable.tableName}")
    fun selectAll(): Flow<List<AssetEntityEmbedded>>

    @Transaction
    @Query("SELECT * FROM ${AssetBalanceTable.tableName} WHERE ${AssetTable.type} IN (:types)")
    fun selectByTypes(types: List<AssetEntityType>): Flow<List<AssetEntityEmbedded>>

    @Transaction
    @Query("SELECT * FROM ${AssetBalanceTable.tableName} WHERE ${AssetTable.id} = :id")
    suspend fun selectById(id: Long): AssetEntityEmbedded

    @Query("SELECT * FROM ${CreditTable.tableName} WHERE ${CreditTable.assetId} = :assetId")
    suspend fun selectCreditByAssetId(assetId: Long): CreditEntity

    @Query("SELECT * FROM ${GoalTable.tableName} WHERE ${GoalTable.assetId} = :assetId")
    suspend fun selectGoalByAssetId(assetId: Long): GoalEntity
}