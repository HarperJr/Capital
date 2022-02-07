package com.harper.capital.repository.account.mapper

import com.harper.capital.database.entity.AccountEntityType
import com.harper.capital.domain.model.AccountType

internal object AccountEntityTypeMapper : (AccountType) -> AccountEntityType {

    override fun invoke(model: AccountType): AccountEntityType = when (model) {
        AccountType.ASSET -> AccountEntityType.ASSET
        AccountType.LIABILITY -> AccountEntityType.LIABILITY
        AccountType.INCOME -> AccountEntityType.INCOME
    }
}
