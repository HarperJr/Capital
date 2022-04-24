package com.harper.capital.transaction.manage.domain

import com.harper.capital.domain.model.Transaction
import com.harper.capital.repository.currency.CurrencyRepository
import com.harper.capital.repository.transaction.TransactionRepository

class AddTransactionUseCase(private val transactionRepository: TransactionRepository) {

    suspend operator fun invoke(transaction: Transaction) {
        transactionRepository.insert(transaction)
    }
}
