package com.harper.capital.transaction.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.harper.capital.R
import com.harper.capital.domain.model.Contact
import com.harper.core.component.Tab
import com.harper.core.component.TabBarData

data class TransactionState(
    val selectedPage: Int,
    val pages: List<TransactionPage> = emptyPages(),
    val bottomSheetState: TransactionBottomSheetState = TransactionBottomSheetState(false)
) {
    val tabBarData: TabBarData
        @Composable
        get() {
            return TabBarData(
                tabs = pages.map { Tab(title = stringResource(id = it.type.resolveTitleRes())) }
            )
        }
}

data class TransactionBottomSheetState(val isExpanded: Boolean, val contacts: List<Contact> = emptyList())

private fun emptyPages(): List<TransactionPage> = TransactionType.values()
    .map { TransactionPage(type = it, accountDataSets = emptyMap()) }

private fun TransactionType.resolveTitleRes(): Int = when (this) {
    TransactionType.LIABILITY -> R.string.expense
    TransactionType.INCOME -> R.string.income
    TransactionType.SEND -> R.string.send
    TransactionType.DEBT -> R.string.debt
}
