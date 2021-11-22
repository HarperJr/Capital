package com.harper.capital.overview.domain

import com.harper.capital.spec.domain.Asset
import com.harper.capital.spec.repository.AssetRepository
import kotlinx.coroutines.coroutineScope

class FetchAssetsUseCase(private val assetRepository: AssetRepository) {

    suspend operator fun invoke(): List<Asset> = coroutineScope {
        assetRepository.fetchAll()
    }
}