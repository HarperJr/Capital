package com.harper.capital.liability.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.harper.capital.R
import com.harper.core.component.Tab
import com.harper.core.component.TabBarData

data class LiabilityManageState(
    val selectedPage: Int,
    val pages: List<LiabilityManagePage> = emptyPages(),
    val bottomSheetState: LiabilityManageBottomSheetState = LiabilityManageBottomSheetState(isExpended = false)
) {
    val tabBarData: TabBarData
        @Composable
        get() {
            return TabBarData(tabs = pages.map { Tab(title = stringResource(id = it.type.resolveTitleRes())) })
        }
}

data class LiabilityManageBottomSheetState(
    val bottomSheet: LiabilityManageBottomSheet? = null,
    val isExpended: Boolean = true
)

private fun emptyPages(): List<LiabilityManagePage> =
    LiabilityManageType.values().map {
        LiabilityManagePage(type = it, name = "", amount = 0.0)
    }

private fun LiabilityManageType.resolveTitleRes(): Int = when (this) {
    LiabilityManageType.LIABILITY -> R.string.expense
    LiabilityManageType.INCOME -> R.string.income
    LiabilityManageType.DEBT -> R.string.debt
}
