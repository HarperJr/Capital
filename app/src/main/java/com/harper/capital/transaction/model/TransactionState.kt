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
    val accountSelection: PagedAccountSelection? = null,
    val bottomSheetState: TransactionBottomSheetState = TransactionBottomSheetState(false)
) {
    val tabBarData: TabBarData
        @Composable
        get() {
            return TabBarData(tabs = pages.map { Tab(title = stringResource(id = it.type.resolveTitleRes())) })
        }
}

data class PagedAccountSelection(val type: TransactionType, val sourceId: Long?, val receiverId: Long?)

data class TransactionBottomSheetState(val isExpanded: Boolean, val contacts: List<Contact> = emptyList())

private fun emptyPages(): List<TransactionPage> = TransactionType.values()
    .map { TransactionPage(type = it, sourceDataSet = null, receiverDataSet = null) }

private fun TransactionType.resolveTitleRes(): Int = when (this) {
    TransactionType.LIABILITY -> R.string.expense
    TransactionType.INCOME -> R.string.income
    TransactionType.SEND -> R.string.send
    TransactionType.DEBT -> R.string.debt
}
