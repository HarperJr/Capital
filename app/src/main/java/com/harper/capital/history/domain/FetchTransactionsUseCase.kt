package com.harper.capital.history.domain

import com.harper.capital.domain.model.Transaction
import com.harper.capital.repository.transaction.TransactionRepository
import kotlinx.coroutines.flow.Flow

class FetchTransactionsUseCase(private val transactionRepository: TransactionRepository) {

    operator fun invoke(accountId: Long?): Flow<List<Transaction>> =
        transactionRepository.fetchTransactions(accountId)
}
