package com.harper.capital.repository.mapper

import com.harper.capital.database.entity.AssetEntity
import com.harper.capital.spec.domain.Asset

internal object AssetEntityMapper : (Asset) -> AssetEntity {

    override fun invoke(model: Asset): AssetEntity = with(model) {
        AssetEntity(
            id = id,
            name = name,
            currencyId = currency.ordinal,
            amount = amount,
            metadata = metadata.let(AssetMetadataEntityMapper)
        )
    }
}
