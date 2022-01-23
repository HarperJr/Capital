package com.harper.capital.category.domain

import com.harper.capital.domain.model.Asset
import com.harper.capital.repository.AssetRepository
import kotlinx.coroutines.coroutineScope

class AddCategoryUseCase(private val assetRepository: AssetRepository) {

    suspend operator fun invoke(asset: Asset) = coroutineScope {
        assetRepository.insert(asset)
    }
}
