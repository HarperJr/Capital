package com.harper.capital.repository.mapper

import com.harper.capital.database.entity.AssetEntity
import com.harper.capital.database.entity.AssetEntityType
import com.harper.capital.domain.model.Asset
import com.harper.core.ext.orElse

internal object AssetEntityMapper : (Asset) -> AssetEntity {

    override fun invoke(model: Asset): AssetEntity = with(model) {
        AssetEntity(
            id = id,
            name = name,
            currencyId = currency.ordinal,
            type = metadata?.assetType?.let(AssetEntityTypeMapper).orElse(AssetEntityType.DEBET),
            icon = model.icon.name,
            color = model.color.name,
            isIncluded = model.isIncluded,
            isArchived = model.isArchived
        )
    }
}
