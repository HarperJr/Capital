package com.harper.capital.repository

import com.harper.capital.database.dao.TransactionDao
import com.harper.capital.domain.model.Transaction
import com.harper.capital.repository.mapper.TransactionEntityMapper
import com.harper.capital.repository.mapper.TransactionMapper
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
}