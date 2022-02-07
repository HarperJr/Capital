package com.harper.capital.transaction.manage.domain

import com.harper.capital.domain.model.TransferTransaction
import com.harper.capital.repository.transaction.TransactionRepository
import kotlinx.coroutines.coroutineScope

class AddTransactionUseCase(private val transactionRepository: TransactionRepository) {

    suspend operator fun invoke(transaction: TransferTransaction) = coroutineScope {
        transactionRepository.insert(transaction)
    }
}
