package com.harper.capital.repository

import com.harper.capital.database.dao.AssetDao
import com.harper.capital.repository.mapper.AssetEntityMapper
import com.harper.capital.repository.mapper.AssetMapper
import com.harper.capital.spec.domain.Asset
import com.harper.capital.spec.repository.AssetRepository

internal class AssetRepositoryImpl(private val assetDao: AssetDao) : AssetRepository {

    override suspend fun insert(asset: Asset) =
        assetDao.insert(AssetEntityMapper(asset))

    override suspend fun fetchAll(): List<Asset> =
        assetDao.selectAll()
            .map(AssetMapper)
}
