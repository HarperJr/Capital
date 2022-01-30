package com.harper.capital.history.domain

import com.harper.capital.domain.model.Transaction
import com.harper.capital.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class FetchTransactionsUseCase(private val transactionRepository: TransactionRepository) {

    operator fun invoke(assetId: Long?): Flow<List<Transaction>> =
        transactionRepository.fetchTransactions(assetId)
}
