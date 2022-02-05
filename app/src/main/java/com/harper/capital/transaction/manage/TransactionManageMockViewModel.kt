package com.harper.capital.transaction.manage

import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.AccountColor
import com.harper.capital.domain.model.AccountIcon
import com.harper.capital.domain.model.AccountType
import com.harper.capital.domain.model.Currency
import com.harper.capital.domain.model.TransactionType
import com.harper.capital.transaction.manage.model.AssetPair
import com.harper.capital.transaction.manage.model.TransactionManageEvent
import com.harper.capital.transaction.manage.model.TransactionManageState
import com.harper.core.ui.ComponentViewModel
import com.harper.core.ui.EventObserver

class TransactionManageMockViewModel : ComponentViewModel<TransactionManageState>(
    defaultState = TransactionManageState(
        transactionType = TransactionType.EXPENSE,
        assetPair = AssetPair(
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
), EventObserver<TransactionManageEvent> {

    override fun onEvent(event: TransactionManageEvent) {
        /**nope**/
    }
}
