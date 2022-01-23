package com.harper.capital.transaction

import com.harper.capital.domain.model.Asset
import com.harper.capital.domain.model.AssetType
import com.harper.capital.transaction.model.AssetDataSet
import com.harper.capital.transaction.model.DataSetSection
import com.harper.capital.transaction.model.TransactionType

class TransactionDataSetFactory {

    fun create(
        type: TransactionType,
        selectedAssetId: Long?,
        assets: List<Asset>
    ): List<AssetDataSet> = when (type) {
        TransactionType.EXPENSE -> createExpenseDataSets(selectedAssetId, assets)
        TransactionType.INCOME -> createIncomeDataSets(selectedAssetId, assets)
        TransactionType.SEND -> createSendDataSets(selectedAssetId, assets)
        TransactionType.GOAL -> emptyList()
        TransactionType.DUTY -> emptyList()
    }

    private fun createExpenseDataSets(
        selectedAssetId: Long?,
        assets: List<Asset>
    ): List<AssetDataSet> = listOf(
        AssetDataSet(
            section = DataSetSection.FROM,
            assets = assets.filter { it.metadata.assetType in AssetType.assetValues() },
            selectedAssetId = selectedAssetId
        ),
        AssetDataSet(
            section = DataSetSection.TO,
            assets = assets.filter { it.metadata.assetType == AssetType.EXPENSE }
        )
    )

    private fun createIncomeDataSets(
        selectedAssetId: Long?,
        assets: List<Asset>
    ): List<AssetDataSet> = listOf(
        AssetDataSet(
            section = DataSetSection.FROM,
            assets = assets.filter { it.metadata.assetType == AssetType.INCOME }
        ),
        AssetDataSet(
            section = DataSetSection.TO,
            assets = assets.filter { it.metadata.assetType in AssetType.assetValues() },
            selectedAssetId = selectedAssetId
        )
    )

    private fun createSendDataSets(
        selectedAssetId: Long?,
        assets: List<Asset>
    ): List<AssetDataSet> = listOf(
        AssetDataSet(
            section = DataSetSection.FROM,
            assets = assets.filter { it.metadata.assetType in AssetType.assetValues() },
            selectedAssetId = selectedAssetId
        ),
        AssetDataSet(
            section = DataSetSection.TO,
            assets = assets.filter { it.metadata.assetType in AssetType.assetValues() }
        ),
    )
}
