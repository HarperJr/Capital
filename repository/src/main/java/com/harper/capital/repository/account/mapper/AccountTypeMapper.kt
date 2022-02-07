package com.harper.capital.repository.account.mapper

import com.harper.capital.database.entity.AccountEntityType
import com.harper.capital.domain.model.AccountType

internal object AccountTypeMapper : (AccountEntityType) -> AccountType {

    override fun invoke(entity: AccountEntityType): AccountType = when (entity) {
        AccountEntityType.ASSET -> AccountType.ASSET
        AccountEntityType.LIABILITY -> AccountType.LIABILITY
        AccountEntityType.INCOME -> AccountType.INCOME
    }
}
