package com.harper.capital.repository

import com.harper.capital.domain.model.Asset
import com.harper.capital.domain.model.AssetType
import kotlinx.coroutines.flow.Flow

interface AssetRepository {

    suspend fun insert(asset: Asset)

    fun fetchByTypes(types: List<AssetType>): Flow<List<Asset>>

    fun fetchAll(): Flow<List<Asset>>

    suspend fun fetchById(id: Long): Asset
}