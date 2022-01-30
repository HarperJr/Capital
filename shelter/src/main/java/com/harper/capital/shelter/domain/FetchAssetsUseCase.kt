package com.harper.capital.shelter.domain

import com.harper.capital.domain.model.Asset
import com.harper.capital.repository.AssetRepository
import kotlinx.coroutines.flow.Flow

class FetchAssetsUseCase(private val assetRepository: AssetRepository) {

    operator fun invoke(): Flow<List<Asset>> = assetRepository.fetchAll()
}
