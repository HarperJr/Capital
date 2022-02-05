package com.harper.capital.repository

import com.harper.capital.database.DatabaseTx
import com.harper.capital.database.dao.TransactionDao
import com.harper.capital.database.entity.LedgerEntity
import com.harper.capital.database.entity.LedgerEntityType
import com.harper.capital.domain.model.Transaction
import com.harper.capital.domain.model.TransferTransaction
import com.harper.capital.repository.mapper.TransactionEntityMapper
import com.harper.capital.repository.mapper.TransactionMapper
import com.harper.core.ext.onNullDefault
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime

internal class TransactionRepositoryImpl(
    private val databaseTx: DatabaseTx,
    private val transactionDao: TransactionDao
) : TransactionRepository {

    override suspend fun insert(transaction: TransferTransaction) {
        databaseTx.runSuspended {
            val transactionId = transactionDao.insert(TransactionEntityMapper(transaction))
            val debitLedger = LedgerEntity(
                transactionId = transactionId,
                accountId = transaction.source.id,
                type = LedgerEntityType.CREDIT,
                amount = transaction.amount
            )
            val creditLedger = LedgerEntity(
                transactionId = transactionId,
                accountId = transaction.receiver.id,
                type = LedgerEntityType.DEBIT,
                amount = transaction.amount
            )
            transactionDao.insertLedgers(listOf(debitLedger, creditLedger))
        }
    }

    override suspend fun deleteById(transactionId: Long) = transactionDao.deleteById(transactionId)

    override fun fetchBalance(): Flow<Double> = transactionDao.selectBalance().onNullDefault(0.0)

    override fun fetchLiabilitiesBetween(dateTimeAfter: LocalDateTime, dateTimeBefore: LocalDateTime): Flow<Double> =
        transactionDao.selectLiabilitiesBetween(
            dateTimeAfter = dateTimeAfter,
            dateTimeBefore = dateTimeBefore
        ).onNullDefault(0.0)

    override fun fetchTransactions(accountId: Long?): Flow<List<Transaction>> =
        transactionDao.selectAll()
            .map { transactions ->
                val filteredTransactions = if (accountId == null) {
                    transactions
                } else {
                    transactions.filter {
                        it.ledgers.any { ledger -> ledger.account.id == accountId }
                    }
                }
                TransactionMapper(filteredTransactions)
            }
}
