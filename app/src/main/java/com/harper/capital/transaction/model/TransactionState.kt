package com.harper.capital.transaction.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.harper.capital.R
import com.harper.capital.domain.model.Asset
import com.harper.core.component.Tab
import com.harper.core.component.TabBarData

data class TransactionState(
    val transactionType: TransactionType,
    val assetsFrom: List<Asset> = emptyList(),
    val assetsTo: List<Asset> = emptyList()
) {
    val tabBarData: TabBarData
        @Composable
        get() {
            val selectedTabIndex = transactionType.ordinal
            return TabBarData(
                tabs = TransactionType.values().map {
                    Tab(title = it.resolveTitle(), isSelected = it == transactionType)
                },
                selectedTabIndex = selectedTabIndex
            )
        }
}

@Composable
private fun TransactionType.resolveTitle(): String = when (this) {
    TransactionType.INCOME -> stringResource(id = R.string.income)
    TransactionType.EXPENSE -> stringResource(id = R.string.expense)
    TransactionType.GOAL -> stringResource(id = R.string.goal)
    TransactionType.DUTY -> stringResource(id = R.string.duty)
}
