package com.harper.capital.overview.domain

import com.harper.capital.spec.domain.Asset
import com.harper.capital.spec.repository.AssetRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow

class FetchAssetsUseCase(private val assetRepository: AssetRepository) {

    suspend operator fun invoke(): Flow<List<Asset>> = coroutineScope {
        assetRepository.fetchAll()
    }
}