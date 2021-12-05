package com.harper.capital.spec.repository

import com.harper.capital.spec.domain.Asset
import kotlinx.coroutines.flow.Flow

interface AssetRepository {

    suspend fun insert(asset: Asset)

    fun fetchAll(): Flow<List<Asset>>
}