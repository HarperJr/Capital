package com.harper.capital.repository

import com.harper.capital.domain.model.Asset
import kotlinx.coroutines.flow.Flow

interface AssetRepository {

    suspend fun insert(asset: Asset)

    fun fetchAll(): Flow<List<Asset>>
}