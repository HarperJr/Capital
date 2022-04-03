package com.harper.capital.repository.transaction

import com.harper.capital.database.DatabaseTx
import com.harper.capital.database.dao.TransactionDao
import com.harper.capital.domain.model.BalancePartition
import com.harper.capital.domain.model.BalancePartitionPeriod
import com.harper.capital.domain.model.Transaction
import com.harper.capital.repository.transaction.mapper.BalancePartitionMapper
import com.harper.capital.repository.transaction.mapper.BalancePartitionPeriodEntityMapper
import com.harper.capital.repository.transaction.mapper.LedgerEntityMapper
import com.harper.capital.repository.transaction.mapper.TransactionEntityMapper
import com.harper.capital.repository.transaction.mapper.TransactionMapper
import com.harper.core.ext.defaultIfNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

internal class TransactionRepositoryImpl(
    private val transactionDao: TransactionDao,
    private val databaseTx: DatabaseTx
) : TransactionRepository {

    override suspend fun insert(transaction: Transaction) {
        databaseTx.runSuspended(context = Dispatchers.IO) {
            val transactionId = transactionDao.insert(TransactionEntityMapper(transaction))
            transactionDao.insertLedgers(
                LedgerEntityMapper(transactionId, transaction.amount, transaction.ledgers)
            )
        }
    }

    override suspend fun fetchTransaction(transactionId: Long): Transaction = withContext(Dispatchers.IO) {
        TransactionMapper(transactionDao.selectById(transactionId))
    }

    override suspend fun update(transaction: Transaction) {
        databaseTx.runSuspended(context = Dispatchers.IO) {
            val existedTransaction = transactionDao.selectById(transaction.id)
            transactionDao.update(TransactionEntityMapper(transaction))
            transactionDao.updateLedgers(
                LedgerEntityMapper(transaction.id, transaction.amount, transaction.ledgers)
                    .map { ledger ->
                        ledger.copy(id = existedTransaction.ledgers.first { it.ledger.type == ledger.type }.ledger.id)
                    }
            )
        }
    }

    override suspend fun deleteById(transactionId: Long) = withContext(Dispatchers.IO) {
        transactionDao.deleteById(transactionId)
    }

    override fun fetchBalance(): Flow<Double> = transactionDao.selectBalance()
        .defaultIfNull(0.0)
        .flowOn(Dispatchers.IO)

    override fun fetchLiabilitiesBetween(dateTimeAfter: LocalDateTime, dateTimeBefore: LocalDateTime): Flow<Double> =
        transactionDao
            .selectLiabilitiesBetween(
                dateTimeAfter = dateTimeAfter,
                dateTimeBefore = dateTimeBefore
            )
            .defaultIfNull(0.0)
            .flowOn(Dispatchers.IO)

    override fun fetchTransactions(accountId: Long?, dateTimeAfter: LocalDateTime, dateTimeBefore: LocalDateTime): Flow<List<Transaction>> =
        transactionDao
            .selectAllBetween(
                dateTimeAfter = dateTimeAfter,
                dateTimeBefore = dateTimeBefore
            )
            .map { transactions ->
                val filteredTransactions = if (accountId == null) {
                    transactions
                } else {
                    transactions.filter {
                        it.ledgers.any { ledger -> ledger.account.id == accountId }
                    }
                }
                filteredTransactions.map(TransactionMapper)
            }
            .flowOn(Dispatchers.IO)

    override fun fetchBalancePartitionsByPeriod(period: BalancePartitionPeriod): Flow<List<BalancePartition>> =
        transactionDao.selectBalancePartitionsByPeriod(BalancePartitionPeriodEntityMapper(period).periodInMillis)
            .map { it.map(BalancePartitionMapper) }
}
