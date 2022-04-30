package com.harper.capital.history.domain

import com.harper.capital.domain.model.Transaction
import com.harper.capital.repository.transaction.TransactionRepository
import java.time.LocalDate
import java.time.LocalDateTime
import kotlinx.coroutines.flow.Flow

class FetchTransactionsUseCase(private val transactionRepository: TransactionRepository) {

    operator fun invoke(accountId: Long?, dateStart: LocalDate, dateEnd: LocalDate): Flow<List<Transaction>> {
        val dateTimeAfter: LocalDateTime
        val dateTimeBefore: LocalDateTime
        if (dateStart == dateEnd) {
            dateTimeAfter = dateStart.withDayOfMonth(1).atStartOfDay()
            dateTimeBefore = dateTimeAfter.plusMonths(1)
        } else {
            dateTimeAfter = dateStart.atStartOfDay()
            dateTimeBefore = dateEnd.atStartOfDay()
        }

        return transactionRepository.fetchTransactions(accountId, dateTimeAfter = dateTimeAfter, dateTimeBefore = dateTimeBefore)
    }
}
