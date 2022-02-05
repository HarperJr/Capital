package com.harper.capital.repository.mapper

import com.harper.capital.database.entity.AccountEntity
import com.harper.capital.domain.model.Account

internal object AccountEntityMapper : (Account) -> AccountEntity {

    override fun invoke(model: Account): AccountEntity = with(model) {
        AccountEntity(
            id = id,
            name = name,
            currencyId = currency.ordinal,
            type = AccountEntityTypeMapper(type),
            icon = model.icon.name,
            color = model.color.name,
            isIncluded = model.isIncluded,
            isArchived = model.isArchived,
            metadataType = AccountMetadataTypeEntityMapper(metadata)
        )
    }
}
