package com.harper.capital.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.harper.capital.database.entity.AssetEntity
import com.harper.capital.database.entity.AssetEntityType
import com.harper.capital.database.entity.AssetTable
import com.harper.capital.database.entity.AssetCreditMetadataEntity
import com.harper.capital.database.entity.AssetCreditMetadataTable
import com.harper.capital.database.entity.AssetGoalMetadataEntity
import com.harper.capital.database.entity.AssetGoalMetadataTable
import com.harper.capital.database.entity.CreditViewTable
import com.harper.capital.database.entity.DebetViewTable
import com.harper.capital.database.entity.TransactionTable
import com.harper.capital.database.entity.embedded.AssetEntityEmbedded
import kotlinx.coroutines.flow.Flow

private const val assetsEmbeddedRequest = """
    SELECT A.*, 
    (SELECT SUM(${CreditViewTable.amount}) FROM ${CreditViewTable.viewName}
    WHERE ${CreditViewTable.assetId} = A.${AssetTable.id}) AS credit,
    (SELECT SUM(${DebetViewTable.amount}) FROM ${DebetViewTable.viewName}
    WHERE ${DebetViewTable.assetId} = A.${AssetTable.id}) AS debet
    FROM ${AssetTable.tableName} A
"""

@Dao
interface AssetDao {

    @Insert
    suspend fun insert(entity: AssetEntity): Long

    @Insert
    suspend fun insertCredit(entity: AssetCreditMetadataEntity)

    @Insert
    suspend fun insertGoal(entity: AssetGoalMetadataEntity)

    @Delete
    suspend fun delete(entity: AssetEntity)

    @Transaction
    @Query(assetsEmbeddedRequest)
    fun selectAll(): Flow<List<AssetEntityEmbedded>>

    @Transaction
    @Query("$assetsEmbeddedRequest WHERE ${AssetTable.type} IN (:types)")
    fun selectByTypes(types: List<AssetEntityType>): Flow<List<AssetEntityEmbedded>>

    @Transaction
    @Query("$assetsEmbeddedRequest WHERE ${AssetTable.id} = :id")
    suspend fun selectById(id: Long): AssetEntityEmbedded

    @Query("SELECT * FROM ${AssetCreditMetadataTable.tableName} WHERE ${AssetCreditMetadataTable.assetId} = :assetId")
    suspend fun selectCreditByAssetId(assetId: Long): AssetCreditMetadataEntity

    @Query("SELECT * FROM ${AssetGoalMetadataTable.tableName} WHERE ${AssetGoalMetadataTable.assetId} = :assetId")
    suspend fun selectGoalByAssetId(assetId: Long): AssetGoalMetadataEntity
}