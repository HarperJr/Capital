package com.harper.capital.repository.transaction

import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.BalancePartition
import com.harper.capital.domain.model.BalancePartitionPeriod
import com.harper.capital.domain.model.Transaction
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface TransactionRepository {

    suspend fun insert(transaction: Transaction)

    suspend fun update(transaction: Transaction)

    suspend fun deleteById(transactionId: Long)

    suspend fun fetchTransaction(transactionId: Long): Transaction

    fun fetchBalance(): Flow<List<Account>>

    fun fetchLiabilitiesBetween(
        dateTimeAfter: LocalDateTime,
        dateTimeBefore: LocalDateTime
    ): Flow<List<Account>>

    fun fetchTransactions(
        accountId: Long? = null,
        dateTimeAfter: LocalDateTime,
        dateTimeBefore: LocalDateTime
    ): Flow<List<Transaction>>

    fun fetchBalancePartitionsByPeriod(period: BalancePartitionPeriod): Flow<List<BalancePartition>>
}
