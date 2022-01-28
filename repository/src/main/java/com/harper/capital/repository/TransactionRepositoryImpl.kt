package com.harper.capital.repository

import com.harper.capital.database.dao.TransactionDao
import com.harper.capital.database.entity.TransactionEntityType
import com.harper.capital.domain.model.Transaction
import com.harper.capital.repository.mapper.TransactionEntityMapper
import com.harper.capital.repository.mapper.TransactionMapper
import com.harper.core.ext.onNullDefault
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class TransactionRepositoryImpl(private val transactionDao: TransactionDao) :
    TransactionRepository {

    override suspend fun insert(transaction: Transaction) =
        transactionDao.insert(TransactionEntityMapper(transaction))

    override suspend fun deleteById(id: Long) = transactionDao.deleteById(id)

    override fun fetchAll(): Flow<List<Transaction>> =
        transactionDao.selectAll()
            .map { it.map(TransactionMapper) }

    override fun fetchByAssetId(assetId: Long): Flow<List<Transaction>> =
        transactionDao.selectByAssetId(assetId)
            .map { it.map(TransactionMapper) }

    override fun fetchDebet(): Flow<Double> =
        transactionDao.selectTransactionsSumByType(TransactionEntityType.EXPENSE).onNullDefault(0.0)

    override fun fetchCredit(): Flow<Double> =
        transactionDao.selectTransactionsSumByType(TransactionEntityType.INCOME).onNullDefault(0.0)
}
