package com.harper.capital.main.domain

import com.harper.capital.main.domain.model.Summary
import com.harper.capital.repository.transaction.TransactionRepository
import com.harper.core.ext.zipWith
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.LocalTime

class FetchSummaryUseCase(private val transactionRepository: TransactionRepository) {

    operator fun invoke(): Flow<Summary> {
        val currentDate = LocalDate.now()
        val startOfMonth = currentDate.withDayOfMonth(1).atStartOfDay()
        val endOfMonth = currentDate.plusMonths(1).atTime(LocalTime.MIDNIGHT)
        return transactionRepository.fetchBalance()
            .zipWith(transactionRepository.fetchLiabilitiesBetween(dateTimeAfter = startOfMonth, dateTimeBefore = endOfMonth))
            .map { (balance, expenses) -> Summary(expenses = expenses, balance = balance) }
    }
}
