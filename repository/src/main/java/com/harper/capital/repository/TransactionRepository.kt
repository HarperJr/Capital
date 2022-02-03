package com.harper.capital.repository

import com.harper.capital.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    suspend fun insert(transaction: Transaction)

    suspend fun deleteById(id: Long)

    fun fetchBalance(): Flow<Double>

    fun fetchExpense(): Flow<Double>

    fun fetchTransactions(assetId: Long? = null): Flow<List<Transaction>>
}
