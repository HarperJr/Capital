package com.harper.capital.transaction.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.harper.capital.R
import com.harper.core.component.Tab
import com.harper.core.component.TabBarData

data class TransactionState(
    val selectedPage: Int = 0,
    val pages: List<TransactionPage> = emptyPages()
) {
    val tabBarData: TabBarData
        @Composable
        get() {
            return TabBarData(
                tabs = pages.map { Tab(title = stringResource(id = it.titleRes)) }
            )
        }
}

private fun emptyPages(): List<TransactionPage> = TransactionType.values().map {
    TransactionPage(titleRes = it.resolveTitleRes(), assetDataSets = emptyList())
}

private fun TransactionType.resolveTitleRes(): Int = when (this) {
    TransactionType.EXPENSE -> R.string.expense
    TransactionType.INCOME -> R.string.income
    TransactionType.SEND -> R.string.send
    TransactionType.GOAL -> R.string.goal
    TransactionType.DUTY -> R.string.duty
}
