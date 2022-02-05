package com.harper.capital.repository.mapper

import com.harper.capital.database.entity.AccountEntity
import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.AccountColor
import com.harper.capital.domain.model.AccountIcon
import com.harper.capital.domain.model.AccountMetadata
import com.harper.capital.domain.model.Currency
import com.harper.core.ext.orElse

internal object AccountMapper : (AccountEntity, AccountMetadata?, Double?) -> Account {

    override fun invoke(entity: AccountEntity, metadata: AccountMetadata?, balance: Double?): Account =
        with(entity) {
            Account(
                id = id,
                name = name,
                type = AccountTypeMapper(entity.type),
                currency = Currency.of(currencyId),
                balance = balance.orElse(0.0),
                metadata = metadata,
                color = AccountColor.valueOf(color),
                icon = AccountIcon.valueOf(icon),
                isIncluded = isIncluded,
                isArchived = isArchived
            )
        }
}
