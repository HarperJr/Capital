package com.harper.capital.repository.mapper

import com.harper.capital.database.entity.AssetEntityType
import com.harper.capital.domain.model.AssetMetadata

internal object AssetTypeEntityMapper : (AssetMetadata) -> AssetEntityType {

    override fun invoke(model: AssetMetadata): AssetEntityType = when (model) {
        is AssetMetadata.Default -> AssetEntityType.DEFAULT
        is AssetMetadata.Credit -> AssetEntityType.CREDIT
        is AssetMetadata.Goal -> AssetEntityType.GOAL
    }
}
