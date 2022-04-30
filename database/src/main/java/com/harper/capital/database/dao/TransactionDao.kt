package com.harper.capital.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.harper.capital.database.entity.AccountTable
import com.harper.capital.database.entity.LedgerEntity
import com.harper.capital.database.entity.LedgerTable
import com.harper.capital.database.entity.TransactionEntity
import com.harper.capital.database.entity.TransactionTable
import com.harper.capital.database.entity.BalancePartitionEntity
import com.harper.capital.database.entity.BalancePartitionTable
import com.harper.capital.database.entity.embedded.AssetEntityEmbedded
import com.harper.capital.database.entity.embedded.TransactionEntityEmbedded
import com.harper.capital.database.view.AssetBalanceTable
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface TransactionDao {

    @Insert
    suspend fun insert(entity: TransactionEntity): Long

    @Insert
    suspend fun insertLedgers(entities: List<LedgerEntity>)

    @Update
    suspend fun update(entity: TransactionEntity)

    @Update
    suspend fun updateLedgers(entities: List<LedgerEntity>)

    @Query("DELETE FROM ${TransactionTable.tableName} WHERE ${TransactionTable.id} = :id")
    suspend fun deleteById(id: Long)

    @Transaction
    @Query("SELECT * FROM ${TransactionTable.tableName} WHERE ${TransactionTable.id} = :id")
    suspend fun selectById(id: Long): TransactionEntityEmbedded

    @Query("SELECT * FROM ${AssetBalanceTable.tableName} WHERE ${AccountTable.type} = 'ASSET' AND ${AccountTable.isIncluded} = 1")
    fun selectBalance(): Flow<List<AssetEntityEmbedded>>

    @Transaction
    @Query(
        """
        SELECT A.id AS ${BalancePartitionTable.accountId}, T.date_time AS ${BalancePartitionTable.period}, 
        T.date_time / :periodInMillis AS period_internal, (
            SELECT SUM(CASE WHEN L2.type = 'DEBIT' THEN L2.amount ELSE -L2.amount END) FROM ${TransactionTable.tableName} T2 
            LEFT JOIN ${LedgerTable.tableName} L2 ON T2.id = L2.transaction_id
            WHERE L2.account_id = A.id AND T2.date_time <= MAX(T.date_time) GROUP BY L2.account_id
        ) AS ${BalancePartitionTable.balance} FROM ${TransactionTable.tableName} T
        LEFT JOIN ${LedgerTable.tableName} L ON T.id = L.transaction_id
        LEFT JOIN ${AccountTable.tableName} A ON A.id = L.account_id
        GROUP BY A.id, period_internal ORDER BY period_internal   
        """
    )
    fun selectBalancePartitionsByPeriod(periodInMillis: Long): Flow<List<BalancePartitionEntity>>

    @Transaction
    @Query(
        """
        SELECT *, SUM(-L.${LedgerTable.amount}) AS ${AssetBalanceTable.balance} FROM ${TransactionTable.tableName} T
        LEFT JOIN ${LedgerTable.tableName} L ON T.${TransactionTable.id} = L.${LedgerTable.transactionId} 
        LEFT JOIN ${AccountTable.tableName} A ON A.${AccountTable.id} = L.${LedgerTable.accountId}
        WHERE A.${AccountTable.type} = 'LIABILITY' AND T.${TransactionTable.dateTime} BETWEEN :dateTimeAfter AND :dateTimeBefore
        GROUP BY A.${AccountTable.id}
        """
    )
    fun selectLiabilitiesBetween(dateTimeAfter: LocalDateTime, dateTimeBefore: LocalDateTime): Flow<List<AssetEntityEmbedded>>

    @Transaction
    @Query(
        """
        SELECT * FROM ${TransactionTable.tableName} WHERE ${TransactionTable.dateTime} 
        BETWEEN :dateTimeAfter AND :dateTimeBefore ORDER BY ${TransactionTable.dateTime} DESC
        """
    )
    fun selectAllBetween(dateTimeAfter: LocalDateTime, dateTimeBefore: LocalDateTime): Flow<List<TransactionEntityEmbedded>>
}
