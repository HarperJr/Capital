package com.harper.capital.history.domain

import com.harper.capital.domain.model.Transaction
import com.harper.capital.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow

class FetchTransactionsUseCase(private val transactionRepository: TransactionRepository) {

    operator fun invoke(assetId: Long?): Flow<List<Transaction>> = if (assetId == null) {
        transactionRepository.fetchAll()
    } else {
        transactionRepository.fetchByAssetId(assetId)
    }
}
