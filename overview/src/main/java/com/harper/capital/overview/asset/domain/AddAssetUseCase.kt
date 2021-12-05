package com.harper.capital.overview.asset.domain

import com.harper.capital.spec.domain.Asset
import com.harper.capital.spec.repository.AssetRepository
import kotlinx.coroutines.coroutineScope

class AddAssetUseCase(private val assetRepository: AssetRepository) {

    suspend operator fun invoke(asset: Asset) = coroutineScope {
        assetRepository.insert(asset)
    }
}
