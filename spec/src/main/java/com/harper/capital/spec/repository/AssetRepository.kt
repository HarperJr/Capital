package com.harper.capital.spec.repository

import com.harper.capital.spec.domain.Asset

interface AssetRepository {

    suspend fun insert(asset: Asset)

    suspend fun fetchAll(): List<Asset>
}