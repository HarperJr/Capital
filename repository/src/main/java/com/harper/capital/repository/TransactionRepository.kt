package com.harper.capital.repository

import com.harper.capital.domain.model.Transaction
import com.harper.capital.domain.model.TransferTransaction
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface TransactionRepository {

    suspend fun insert(transaction: TransferTransaction)

    suspend fun deleteById(transactionId: Long)

    fun fetchBalance(): Flow<Double>

    fun fetchLiabilitiesBetween(
        dateTimeAfter: LocalDateTime,
        dateTimeBefore: LocalDateTime
    ): Flow<Double>

    fun fetchTransactions(accountId: Long? = null): Flow<List<Transaction>>
}
