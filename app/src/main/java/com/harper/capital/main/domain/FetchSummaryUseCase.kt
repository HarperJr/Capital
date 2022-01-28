package com.harper.capital.main.domain

import com.harper.capital.main.domain.model.Summary
import com.harper.capital.repository.TransactionRepository
import com.harper.core.ext.zipWith
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FetchSummaryUseCase(private val transactionRepository: TransactionRepository) {

    operator fun invoke(): Flow<Summary> = transactionRepository.fetchDebet()
        .zipWith(transactionRepository.fetchCredit())
        .map { (debet, credit) -> Summary(debet = -debet, amount = credit - debet) }
}
