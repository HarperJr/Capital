package com.harper.capital.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.harper.capital.database.entity.AccountTable
import com.harper.capital.database.entity.LedgerEntity
import com.harper.capital.database.entity.LedgerTable
import com.harper.capital.database.entity.TransactionEntity
import com.harper.capital.database.entity.TransactionTable
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

    @Query("DELETE FROM ${TransactionTable.tableName} WHERE ${TransactionTable.id} = :id")
    suspend fun deleteById(id: Long)

    @Query(
        """
        SELECT SUM(${AssetBalanceTable.balance}) FROM ${AssetBalanceTable.tableName} 
        WHERE ${AccountTable.type} IN ('ASSET') AND ${AccountTable.isIncluded} = 1
        """
    )
    fun selectBalance(): Flow<Double>

    @Query(
        """
        SELECT SUM(-L.${LedgerTable.amount}) FROM ${TransactionTable.tableName} T
        LEFT JOIN ${LedgerTable.tableName} L ON T.${TransactionTable.id} = L.${LedgerTable.transactionId} 
        LEFT JOIN ${AccountTable.tableName} A ON A.${AccountTable.id} = L.${LedgerTable.accountId}
        WHERE A.${AccountTable.type} = 'LIABILITY' AND T.${TransactionTable.dateTime} BETWEEN :dateTimeAfter AND :dateTimeBefore
        GROUP BY A.${AccountTable.id}
        """
    )
    fun selectLiabilitiesBetween(dateTimeAfter: LocalDateTime, dateTimeBefore: LocalDateTime): Flow<Double>

    @Transaction
    @Query("SELECT * FROM ${TransactionTable.tableName}")
    fun selectAll(): Flow<List<TransactionEntityEmbedded>>
}
