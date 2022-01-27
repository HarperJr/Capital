package com.harper.capital.repository.mapper

import com.harper.capital.database.entity.AssetEntityType
import com.harper.capital.domain.model.AssetType

internal object AssetEntityTypeMapper : (AssetType) -> AssetEntityType {

    override fun invoke(model: AssetType): AssetEntityType = when (model) {
        AssetType.DEBET -> AssetEntityType.DEBET
        AssetType.CREDIT -> AssetEntityType.CREDIT
        AssetType.GOAL -> AssetEntityType.GOAL
        AssetType.EXPENSE -> AssetEntityType.EXPENSE
        AssetType.INCOME -> AssetEntityType.INCOME
    }
}
