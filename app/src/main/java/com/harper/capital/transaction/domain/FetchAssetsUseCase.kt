package com.harper.capital.transaction.domain

import com.harper.capital.repository.AssetRepository
import kotlinx.coroutines.coroutineScope

class FetchAssetsUseCase(private val assetRepository: AssetRepository) {

    suspend operator fun invoke() = coroutineScope {
        assetRepository.fetchAll()
    }
}
