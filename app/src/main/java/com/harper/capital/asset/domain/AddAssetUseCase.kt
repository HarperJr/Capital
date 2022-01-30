package com.harper.capital.asset.domain

import com.harper.capital.domain.model.Asset
import com.harper.capital.domain.model.AssetColor
import com.harper.capital.domain.model.AssetIcon
import com.harper.capital.domain.model.AssetMetadata
import com.harper.capital.domain.model.AssetType
import com.harper.capital.domain.model.Currency
import com.harper.capital.repository.AssetRepository
import kotlinx.coroutines.coroutineScope

class AddAssetUseCase(private val assetRepository: AssetRepository) {

    suspend operator fun invoke(
        name: String,
        balance: Double,
        currency: Currency,
        color: AssetColor,
        icon: AssetIcon,
        type: AssetType
    ) = coroutineScope {
        assetRepository.insert(
            Asset(
                id = 0L,
                name = name,
                balance = balance,
                currency = currency,
                color = color,
                icon = icon,
                metadata = when (type) {
                    AssetType.DEBET -> AssetMetadata.Debet
                    AssetType.CREDIT -> AssetMetadata.Credit(limit = 0.0)
                    AssetType.GOAL -> AssetMetadata.Goal(goal = 0.0)
                    AssetType.INCOME -> AssetMetadata.Income
                    AssetType.EXPENSE -> AssetMetadata.Expense
                }
            )
        )
    }
}
