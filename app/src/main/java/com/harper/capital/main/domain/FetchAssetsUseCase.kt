package com.harper.capital.main.domain

import com.harper.capital.domain.model.Asset
import com.harper.capital.domain.model.AssetType
import com.harper.capital.repository.AssetRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow

class FetchAssetsUseCase(private val assetRepository: AssetRepository) {

    suspend operator fun invoke(): Flow<List<Asset>> = coroutineScope {
        assetRepository.fetchByTypes(AssetType.assetValues())
    }
}
