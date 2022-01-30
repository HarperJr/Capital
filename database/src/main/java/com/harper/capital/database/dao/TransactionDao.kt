package com.harper.capital.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.harper.capital.database.entity.AssetTable
import com.harper.capital.database.entity.LedgerEntity
import com.harper.capital.database.entity.LedgerTable
import com.harper.capital.database.entity.TransactionEntity
import com.harper.capital.database.entity.TransactionTable
import com.harper.capital.database.entity.embedded.TransactionEntityEmbedded
import com.harper.capital.database.view.AssetBalanceTable
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert
    suspend fun insert(entity: TransactionEntity): Long

    @Insert
    suspend fun insert(entities: List<LedgerEntity>)

    @Query("DELETE FROM ${TransactionTable.tableName} WHERE ${TransactionTable.id} = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT SUM(${AssetBalanceTable.balance}) FROM ${AssetBalanceTable.tableName} WHERE ${AssetTable.type} IN ('CREDIT', 'DEBET')")
    fun selectBalance(): Flow<Double>

    @Query(
        """
        SELECT SUM(-L.${LedgerTable.amount}) FROM ${TransactionTable.tableName} T
        LEFT JOIN ${LedgerTable.tableName} L ON T.${TransactionTable.id} = L.${LedgerTable.transactionId} 
        LEFT JOIN ${AssetTable.tableName} A ON A.${AssetTable.id} = L.${LedgerTable.assetId}
        WHERE A.${AssetTable.type} = 'EXPENSE' GROUP BY A.${AssetTable.id}
    """
    )
    fun selectExpenses(): Flow<Double>

    @Transaction
    @Query("SELECT * FROM ${TransactionTable.tableName}")
    fun selectAllTransactions(): Flow<List<TransactionEntityEmbedded>>
}
