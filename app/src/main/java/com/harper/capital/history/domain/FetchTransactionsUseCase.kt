package com.harper.capital.history.domain

import com.harper.capital.domain.model.Transaction
import com.harper.capital.repository.transaction.TransactionRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class FetchTransactionsUseCase(private val transactionRepository: TransactionRepository) {

    operator fun invoke(accountId: Long?, month: LocalDate): Flow<List<Transaction>> {
        val startOfMonth = month.withDayOfMonth(1).atStartOfDay()
        val endOfMonth = startOfMonth.plusMonths(1)
        return transactionRepository.fetchTransactions(accountId, dateTimeAfter = startOfMonth, dateTimeBefore = endOfMonth)
    }
}
