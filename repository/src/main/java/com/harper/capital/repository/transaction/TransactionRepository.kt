package com.harper.capital.repository.transaction

import com.harper.capital.domain.model.Transaction
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface TransactionRepository {

    suspend fun insert(transaction: Transaction)

    suspend fun update(transaction: Transaction)

    suspend fun deleteById(transactionId: Long)

    suspend fun fetchTransaction(transactionId: Long): Transaction

    fun fetchBalance(): Flow<Double>

    fun fetchLiabilitiesBetween(
        dateTimeAfter: LocalDateTime,
        dateTimeBefore: LocalDateTime
    ): Flow<Double>

    fun fetchTransactions(accountId: Long? = null): Flow<List<Transaction>>
}
