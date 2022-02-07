package com.harper.capital.transaction.manage.domain

import com.harper.capital.repository.transaction.TransactionRepository
import kotlinx.coroutines.coroutineScope

class FetchTransactionUseCase(private val transactionRepository: TransactionRepository) {

    suspend operator fun invoke(transactionId: Long) = coroutineScope {
        transactionRepository.fetchTransaction(transactionId)
    }
}
