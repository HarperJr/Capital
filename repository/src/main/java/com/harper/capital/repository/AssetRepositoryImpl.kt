package com.harper.capital.repository

import com.harper.capital.database.DatabaseTx
import com.harper.capital.database.dao.AssetDao
import com.harper.capital.database.entity.AssetEntityType
import com.harper.capital.database.entity.CreditEntity
import com.harper.capital.database.entity.GoalEntity
import com.harper.capital.database.entity.embedded.AssetEntityEmbedded
import com.harper.capital.domain.model.Asset
import com.harper.capital.domain.model.AssetMetadata
import com.harper.capital.domain.model.AssetType
import com.harper.capital.repository.mapper.AssetEntityMapper
import com.harper.capital.repository.mapper.AssetEntityTypeMapper
import com.harper.capital.repository.mapper.AssetMapper
import com.harper.core.ext.cast
import com.harper.core.ext.orElse
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class AssetRepositoryImpl(
    private val assetDao: AssetDao,
    private val databaseTx: DatabaseTx
) :
    AssetRepository {

    override suspend fun insert(asset: Asset) = databaseTx.runSuspended {
        val assetEntity = AssetEntityMapper(asset)
        val assetId = assetDao.insert(assetEntity)
        when (assetEntity.type) {
            AssetEntityType.CREDIT -> {
                val metadata = asset.metadata.cast<AssetMetadata.Credit>()
                assetDao.insertCredit(
                    CreditEntity(
                        assetId = assetId,
                        limit = metadata.limit
                    )
                )
            }
            AssetEntityType.GOAL -> {
                val metadata = asset.metadata.cast<AssetMetadata.Goal>()
                assetDao.insertGoal(
                    GoalEntity(
                        assetId = assetId,
                        goal = metadata.goal
                    )
                )
            }
            else -> return@runSuspended
        }
    }

    @OptIn(FlowPreview::class)
    override fun fetchByTypes(types: List<AssetType>): Flow<List<Asset>> =
        assetDao.selectByTypes(types.map(AssetEntityTypeMapper))
            .map { entities -> entities.map { mapToAsset(it) } }

    @OptIn(FlowPreview::class)
    override fun fetchAll(): Flow<List<Asset>> =
        assetDao.selectAll()
            .map { entities -> entities.map { mapToAsset(it) } }

    override suspend fun fetchById(id: Long): Asset =
        mapToAsset(assetDao.selectById(id))

    override suspend fun update(asset: Asset) =
        assetDao.update(AssetEntityMapper(asset))

    private suspend fun mapToAsset(entity: AssetEntityEmbedded): Asset = entity.let {
        val metadata = when (it.asset.type) {
            AssetEntityType.DEBET -> AssetMetadata.Debet
            AssetEntityType.CREDIT -> {
                val credit = assetDao.selectCreditByAssetId(it.asset.id)
                AssetMetadata.Credit(credit.limit)
            }
            AssetEntityType.GOAL -> {
                val goal = assetDao.selectGoalByAssetId(it.asset.id)
                AssetMetadata.Goal(goal.goal)
            }
            AssetEntityType.EXPENSE -> AssetMetadata.Expense
            AssetEntityType.INCOME -> AssetMetadata.Income
        }
        AssetMapper(it.asset, metadata)
            .copy(balance = it.balance.orElse(0.0))
    }
}
