package com.harper.capital.repository.mapper

import com.harper.capital.database.entity.AssetEntity
import com.harper.capital.spec.domain.Asset
import com.harper.capital.spec.domain.Currency

object AssetMapper : (AssetEntity) -> Asset {

    override fun invoke(entity: AssetEntity): Asset = with(entity) {
        Asset(
            id = id,
            name = name,
            currency = Currency.of(currencyId),
            amount = amount,
            metadata = entity.metadata.let(AssetMetadataMapper)
        )
    }
}
