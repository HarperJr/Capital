package com.harper.capital.liability.model

import com.harper.capital.domain.model.AccountIcon
import com.harper.capital.domain.model.AccountMetadata
import com.harper.capital.domain.model.Currency

data class LiabilityManagePage(
    val type: LiabilityManageType,
    val name: String = "",
    val amount: Double = 0.0,
    val currency: Currency = Currency.RUB,
    val icon: AccountIcon = AccountIcon.PRODUCTS,
    val metadata: AccountMetadata? = null
)
