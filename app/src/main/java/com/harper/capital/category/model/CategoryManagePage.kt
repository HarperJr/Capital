package com.harper.capital.category.model

import androidx.annotation.StringRes
import com.harper.capital.domain.model.CategoryIcon
import com.harper.capital.domain.model.Currency

data class CategoryManagePage(
    @StringRes val titleRes: Int,
    val name: String,
    val amount: Double,
    val currency: Currency = Currency.RUB,
    val icon: CategoryIcon = CategoryIcon.PRODUCTS
)
