package com.harper.capital.main.domain

import com.harper.capital.domain.model.TransferTransaction
import com.harper.capital.repository.transaction.TransactionRepository
import java.time.LocalDateTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val FAVORITE_TRANSACTIONS_COUNT_BOUND = 5

class FetchFavoriteTransactionsUseCase(private val transactionRepository: TransactionRepository) {

    operator fun invoke(): Flow<List<TransferTransaction>> {
        val dateTimeBefore = LocalDateTime.now()
        val dateTimeAfter = dateTimeBefore.minusYears(99L)
        return transactionRepository.fetchTransactions(dateTimeAfter = dateTimeAfter, dateTimeBefore = dateTimeBefore)
            .map { transactions ->
                transactions
                    .filterIsInstance<TransferTransaction>()
                    .groupBy { it.source.id shr 16 or it.receiver.id }
                    .filter { (_, transactions) -> transactions.size >= FAVORITE_TRANSACTIONS_COUNT_BOUND }
                    .map { (_, transactions) -> transactions.first() }
            }
    }
}