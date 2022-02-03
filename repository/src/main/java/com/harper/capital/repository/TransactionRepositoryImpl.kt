package com.harper.capital.repository

import com.harper.capital.database.DatabaseTx
import com.harper.capital.database.dao.TransactionDao
import com.harper.capital.database.entity.LedgerEntity
import com.harper.capital.database.entity.LedgerEntityType
import com.harper.capital.domain.model.Transaction
import com.harper.capital.repository.mapper.TransactionEntityMapper
import com.harper.capital.repository.mapper.TransactionMapper
import com.harper.core.ext.onNullDefault
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class TransactionRepositoryImpl(
    private val databaseTx: DatabaseTx,
    private val transactionDao: TransactionDao
) : TransactionRepository {

    override suspend fun insert(transaction: Transaction) {
        databaseTx.runSuspended {
            val transactionId = transactionDao.insert(TransactionEntityMapper(transaction))
            val debitLedger = LedgerEntity(
                transactionId = transactionId,
                assetId = transaction.source.id,
                type = LedgerEntityType.DEBET,
                amount = transaction.amount
            )
            val creditLedger = LedgerEntity(
                transactionId = transactionId,
                assetId = transaction.receiver.id,
                type = LedgerEntityType.CREDIT,
                amount = transaction.amount
            )
            transactionDao.insert(listOf(debitLedger, creditLedger))
        }
    }

    override suspend fun deleteById(id: Long) = transactionDao.deleteById(id)

    override fun fetchBalance(): Flow<Double> = transactionDao.selectBalance().onNullDefault(0.0)

    override fun fetchExpense(): Flow<Double> = transactionDao.selectExpenses().onNullDefault(0.0)

    override fun fetchTransactions(assetId: Long?): Flow<List<Transaction>> =
        transactionDao.selectAllTransactions()
            .map { transactions ->
                val filteredTransactions = if (assetId == null) {
                    transactions
                } else {
                    transactions.filter {
                        it.ledgers.any { ledger -> ledger.asset.id == assetId } && it.ledgers.size > 1
                    }
                }
                TransactionMapper(filteredTransactions)
            }
}
