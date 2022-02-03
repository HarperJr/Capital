package com.harper.capital.asset.domain

import com.harper.capital.domain.model.Asset
import com.harper.capital.domain.model.AssetColor
import com.harper.capital.domain.model.AssetIcon
import com.harper.capital.domain.model.AssetMetadata
import com.harper.capital.domain.model.AssetType
import com.harper.capital.domain.model.Currency
import com.harper.capital.repository.AssetRepository
import kotlinx.coroutines.coroutineScope

class UpdateAssetUseCase(private val assetRepository: AssetRepository) {

    suspend operator fun invoke(
        id: Long,
        name: String,
        amount: Double,
        currency: Currency,
        color: AssetColor,
        icon: AssetIcon,
        type: AssetType,
        isIncluded: Boolean,
        isActive: Boolean
    ) = coroutineScope {
        assetRepository.update(
            Asset(
                id = id,
                name = name,
                balance = amount,
                currency = currency,
                color = color,
                icon = icon,
                isIncluded = isIncluded,
                isArchived = isActive,
                metadata = when (type) {
                    AssetType.DEBET -> AssetMetadata.Debet
                    AssetType.CREDIT -> AssetMetadata.Credit(0.0)
                    AssetType.GOAL -> AssetMetadata.Goal(0.0)
                    AssetType.INCOME -> AssetMetadata.Income
                    AssetType.EXPENSE -> AssetMetadata.Expense
                }
            )
        )
    }
}
