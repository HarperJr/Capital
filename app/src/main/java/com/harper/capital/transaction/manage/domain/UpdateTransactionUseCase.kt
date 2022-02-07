package com.harper.capital.transaction.manage.domain

import com.harper.capital.domain.model.Transaction
import com.harper.capital.repository.transaction.TransactionRepository
import kotlinx.coroutines.coroutineScope

class UpdateTransactionUseCase(private val transactionRepository: TransactionRepository) {

    suspend operator fun invoke(transaction: Transaction) = coroutineScope {
        transactionRepository.update(transaction)
    }
}
