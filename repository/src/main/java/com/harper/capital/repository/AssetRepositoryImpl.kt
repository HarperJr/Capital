package com.harper.capital.repository

import com.harper.capital.database.DatabaseTx
import com.harper.capital.database.dao.AssetDao
import com.harper.capital.database.dao.TransactionDao
import com.harper.capital.database.entity.AssetEntityType
import com.harper.capital.database.entity.CreditEntity
import com.harper.capital.database.entity.GoalEntity
import com.harper.capital.database.entity.LedgerEntity
import com.harper.capital.database.entity.LedgerEntityType
import com.harper.capital.database.entity.TransactionEntity
import com.harper.capital.database.entity.embedded.AssetEntityEmbedded
import com.harper.capital.domain.model.Asset
import com.harper.capital.domain.model.AssetMetadata
import com.harper.capital.domain.model.AssetType
import com.harper.capital.repository.mapper.AssetEntityMapper
import com.harper.capital.repository.mapper.AssetEntityTypeMapper
import com.harper.capital.repository.mapper.AssetMapper
import com.harper.core.ext.cast
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import kotlin.math.abs

internal class AssetRepositoryImpl(
    private val assetDao: AssetDao,
    private val transactionDao: TransactionDao,
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
            else -> {
            }
        }
        if (asset.balance > 0.0) {
            insertChangeBalanceTransaction(assetId, asset.balance, LedgerEntityType.CREDIT)
        }
    }

    override fun fetchByTypes(types: List<AssetType>): Flow<List<Asset>> =
        assetDao.selectByTypes(types.map(AssetEntityTypeMapper))
            .map { entities -> entities.map { mapToAsset(it) } }

    override fun fetchAll(): Flow<List<Asset>> =
        assetDao.selectAll()
            .map { entities -> entities.map { mapToAsset(it) } }

    override suspend fun fetchById(id: Long): Asset =
        mapToAsset(assetDao.selectById(id))

    override suspend fun update(asset: Asset) {
        val prevAsset = fetchById(asset.id)
        assetDao.update(AssetEntityMapper(asset))
        val balanceDiff = asset.balance - prevAsset.balance
        if (balanceDiff != 0.0) {
            val ledgerType =
                if (balanceDiff > 0) LedgerEntityType.CREDIT else LedgerEntityType.DEBET
            databaseTx.runSuspended {
                insertChangeBalanceTransaction(asset.id, abs(balanceDiff), ledgerType)
            }
        }
    }

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
        AssetMapper(it.asset, metadata, it.balance)
    }

    private suspend fun insertChangeBalanceTransaction(
        assetId: Long,
        balance: Double,
        ledgerType: LedgerEntityType
    ) {
        val transactionEntity = TransactionEntity(
            id = 0L,
            dateTime = LocalDateTime.now(),
            comment = null,
            isScheduled = false
        )
        val transactionId = transactionDao.insert(transactionEntity)
        val ledger = LedgerEntity(
            transactionId = transactionId,
            assetId = assetId,
            type = ledgerType,
            amount = balance
        )
        transactionDao.insert(listOf(ledger))
    }
}
