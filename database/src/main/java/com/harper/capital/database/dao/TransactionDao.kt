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
import com.harper.capital.database.entity.embedded.LiabilitiesEntityEmbedded
import com.harper.capital.database.entity.embedded.LiabilitiesTable
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

    @Query(
        """
        SELECT SUM(${AssetBalanceTable.balance}) FROM ${AssetBalanceTable.tableName} 
        WHERE ${AccountTable.type} = 'ASSET' AND ${AccountTable.isIncluded} = 1
        """
    )
    fun selectBalance(): Flow<Double>

    @Query(
        """
        SELECT SUM(-L.${LedgerTable.amount}) FROM ${TransactionTable.tableName} T
        LEFT JOIN ${LedgerTable.tableName} L ON T.${TransactionTable.id} = L.${LedgerTable.transactionId} 
        LEFT JOIN ${AccountTable.tableName} A ON A.${AccountTable.id} = L.${LedgerTable.accountId}
        WHERE A.${AccountTable.type} = 'LIABILITY' AND T.${TransactionTable.dateTime} BETWEEN :dateTimeAfter AND :dateTimeBefore
        """
    )
    fun selectLiabilitiesBetween(dateTimeAfter: LocalDateTime, dateTimeBefore: LocalDateTime): Flow<Double>

    @Transaction
    @Query(
        """
        SELECT * FROM ${TransactionTable.tableName} WHERE ${TransactionTable.dateTime} 
        BETWEEN :dateTimeAfter AND :dateTimeBefore ORDER BY ${TransactionTable.dateTime} DESC
        """
    )
    fun selectAllBetween(dateTimeAfter: LocalDateTime, dateTimeBefore: LocalDateTime): Flow<List<TransactionEntityEmbedded>>
}
