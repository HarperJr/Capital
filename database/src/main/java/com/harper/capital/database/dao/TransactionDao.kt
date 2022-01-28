package com.harper.capital.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.harper.capital.database.entity.TransactionEntity
import com.harper.capital.database.entity.TransactionEntityType
import com.harper.capital.database.entity.TransactionTable
import com.harper.capital.database.entity.embedded.TransactionEntityEmbedded
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert
    suspend fun insert(entity: TransactionEntity)

    @Transaction
    @Query("SELECT * FROM ${TransactionTable.tableName}")
    fun selectAll(): Flow<List<TransactionEntityEmbedded>>

    @Transaction
    @Query("SELECT * FROM ${TransactionTable.tableName} WHERE ${TransactionTable.assetFromId} = :assetId OR ${TransactionTable.assetToId} = :assetId")
    fun selectByAssetId(assetId: Long): Flow<List<TransactionEntityEmbedded>>

    @Query("SELECT SUM(${TransactionTable.amount}) FROM ${TransactionTable.tableName} WHERE ${TransactionTable.type} = :type")
    fun selectTransactionsSumByType(type: TransactionEntityType): Flow<Double>

    @Query("DELETE FROM ${TransactionTable.tableName} WHERE ${TransactionTable.id} = :id")
    suspend fun deleteById(id: Long)
}
