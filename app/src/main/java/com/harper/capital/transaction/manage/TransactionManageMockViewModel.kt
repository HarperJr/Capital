package com.harper.capital.transaction.manage

import com.harper.capital.domain.model.*
import com.harper.capital.transaction.manage.model.TransactionManageEvent
import com.harper.capital.transaction.manage.model.TransactionManageMode
import com.harper.capital.transaction.manage.model.TransactionManageState
import com.harper.core.ui.ComponentViewModel

class TransactionManageMockViewModel : ComponentViewModel<TransactionManageState, TransactionManageEvent>(
    initialState = TransactionManageState(
        mode = TransactionManageMode.ADD,
        accounts = listOf(
            Account(
                id = 0L,
                name = "Tinkoff",
                type = AccountType.ASSET,
                balance = 1000.0,
                currency = Currency.RUB,
                color = AccountColor.TINKOFF,
                icon = AccountIcon.TINKOFF,
                metadata = null
            ),
            Account(
                id = 0L,
                name = "Products",
                type = AccountType.LIABILITY,
                balance = 1000.0,
                currency = Currency.RUB,
                color = AccountColor.CATEGORY,
                icon = AccountIcon.PRODUCTS,
                metadata = null
            )
        ),
        isLoading = false
    )
) {

    override fun onEvent(event: TransactionManageEvent) {
        /**nope**/
    }
}
