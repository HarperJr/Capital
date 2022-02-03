package com.harper.capital.repository.mapper

import com.harper.capital.database.entity.AssetEntity
import com.harper.capital.domain.model.Asset
import com.harper.capital.domain.model.AssetColor
import com.harper.capital.domain.model.AssetIcon
import com.harper.capital.domain.model.AssetMetadata
import com.harper.capital.domain.model.Currency
import com.harper.core.ext.orElse

internal object AssetMapper : (AssetEntity, AssetMetadata?, Double?) -> Asset {

    override fun invoke(entity: AssetEntity, metadata: AssetMetadata?, balance: Double?): Asset =
        with(entity) {
            Asset(
                id = id,
                name = name,
                currency = Currency.of(currencyId),
                balance = balance.orElse(0.0),
                metadata = metadata,
                color = AssetColor.valueOf(color),
                icon = AssetIcon.valueOf(icon),
                isIncluded = isIncluded,
                isArchived = isArchived
            )
        }
}
