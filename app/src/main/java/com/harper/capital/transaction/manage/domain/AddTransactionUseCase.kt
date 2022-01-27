package com.harper.capital.transaction.manage.domain

import com.harper.capital.domain.model.Transaction
import com.harper.capital.repository.TransactionRepository
import kotlinx.coroutines.coroutineScope

class AddTransactionUseCase(private val transactionRepository: TransactionRepository) {

    suspend operator fun invoke(transaction: Transaction) = coroutineScope {
        transactionRepository.insert(transaction)
    }
}
