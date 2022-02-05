package com.harper.capital.category.model

import com.harper.capital.domain.model.AccountIcon
import com.harper.capital.domain.model.Currency

data class CategoryManagePage(
    val type: CategoryManageType,
    val name: String,
    val amount: Double,
    val currency: Currency = Currency.RUB,
    val icon: AccountIcon = AccountIcon.PRODUCTS
)
