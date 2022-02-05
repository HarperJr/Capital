package com.harper.capital.category.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.harper.capital.R
import com.harper.core.component.Tab
import com.harper.core.component.TabBarData

data class CategoryManageState(
    val selectedPage: Int,
    val pages: List<CategoryManagePage> = emptyPages(),
    val bottomSheetState: CategoryManageBottomSheetState = CategoryManageBottomSheetState(isExpended = false)
) {
    val tabBarData: TabBarData
        @Composable
        get() {
            return TabBarData(tabs = pages.map { Tab(title = stringResource(id = it.type.resolveTitleRes())) })
        }
}

data class CategoryManageBottomSheetState(
    val bottomSheet: CategoryManageBottomSheet? = null,
    val isExpended: Boolean = true
)

private fun emptyPages(): List<CategoryManagePage> =
    CategoryManageType.values().map {
        CategoryManagePage(type = it, name = "", amount = 0.0)
    }

private fun CategoryManageType.resolveTitleRes(): Int = when (this) {
    CategoryManageType.LIABILITY -> R.string.expense
    CategoryManageType.INCOME -> R.string.income
}
