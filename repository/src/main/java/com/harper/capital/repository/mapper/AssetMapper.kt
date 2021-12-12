package com.harper.capital.repository.mapper

import com.harper.capital.database.entity.AssetEntity
import com.harper.capital.domain.model.Asset
import com.harper.capital.domain.model.AssetColor
import com.harper.capital.domain.model.AssetIcon
import com.harper.capital.domain.model.AssetMetadata
import com.harper.capital.domain.model.Currency

internal object AssetMapper : (AssetEntity, AssetMetadata) -> Asset {

    override fun invoke(entity: AssetEntity, metadata: AssetMetadata): Asset = with(entity) {
        Asset(
            id = id,
            name = name,
            currency = Currency.of(currencyId),
            amount = amount,
            metadata = metadata,
            color = AssetColor.valueOf(color),
            icon = AssetIcon.valueOf(icon)
        )
    }
}
