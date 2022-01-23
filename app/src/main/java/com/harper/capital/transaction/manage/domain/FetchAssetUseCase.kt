package com.harper.capital.transaction.manage.domain

import com.harper.capital.repository.AssetRepository
import kotlinx.coroutines.coroutineScope

class FetchAssetUseCase(private val assetRepository: AssetRepository) {

    suspend operator fun invoke(id: Long) = coroutineScope {
        assetRepository.fetchById(id)
    }
}
