package com.harper.capital.repository.mapper

import com.harper.capital.database.entity.AssetEntity
import com.harper.capital.domain.model.Asset

internal object AssetEntityMapper : (Asset) -> AssetEntity {

    override fun invoke(model: Asset): AssetEntity = with(model) {
        AssetEntity(
            id = id,
            name = name,
            currencyId = currency.ordinal,
            amount = amount,
            type = metadata.assetType.let(AssetTypeEntityMapper),
            icon = model.icon.name,
            color = model.color.name
        )
    }
}
