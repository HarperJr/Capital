package com.harper.capital.category.model

import com.harper.capital.domain.model.Currency
import com.harper.capital.domain.model.ExpenseIcon

data class ExpenseCategoryAddState(
    val name: String = "",
    val icon: ExpenseIcon = ExpenseIcon.PRODUCTS,
    val amount: Double = 0.0,
    val currency: Currency = Currency.RUB,
    val bottomSheetState: ExpenseCategoryAddBottomSheetState = ExpenseCategoryAddBottomSheetState(isExpended = false)
)

data class ExpenseCategoryAddBottomSheetState(
    val bottomSheet: CategoryManageBottomSheet? = null,
    val isExpended: Boolean = true
)
