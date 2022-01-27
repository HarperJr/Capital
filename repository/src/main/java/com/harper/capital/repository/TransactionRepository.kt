package com.harper.capital.repository

import com.harper.capital.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    suspend fun insert(transaction: Transaction)

    suspend fun deleteById(id: Long)

    fun fetchAll(): Flow<List<Transaction>>

    fun fetchByAssetId(assetId: Long): Flow<List<Transaction>>
}
