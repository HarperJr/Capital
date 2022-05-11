package com.harper.capital.transaction.manage

import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.AccountColor
import com.harper.capital.domain.model.AccountIcon
import com.harper.capital.domain.model.AccountType
import com.harper.capital.domain.model.Currency
import com.harper.capital.domain.model.TransferTransaction
import com.harper.capital.transaction.manage.model.TransactionManageEvent
import com.harper.capital.transaction.manage.model.TransactionManageMode
import com.harper.capital.transaction.manage.model.TransactionManageState
import com.harper.core.ui.ComponentViewModel
import java.time.LocalDateTime

class TransactionManageMockViewModel : ComponentViewModel<TransactionManageState, TransactionManageEvent>(
    initialState = TransactionManageState(
        mode = TransactionManageMode.ADD,
        transaction = TransferTransaction(
            source = Account(
                id = 0L,
                name = "Tinkoff",
                type = AccountType.ASSET,
                balance = 1000.0,
                currency = Currency.RUB,
                color = AccountColor.TINKOFF,
                icon = AccountIcon.TINKOFF,
                metadata = null
            ),
            receiver = Account(
                id = 0L,
                name = "Products",
                type = AccountType.LIABILITY,
                balance = 1000.0,
                currency = Currency.RUB,
                color = AccountColor.LIABILITY,
                icon = AccountIcon.PRODUCTS,
                metadata = null
            ),
            sourceAmount = 1000.0,
            receiverAmount = 1000.0,
            dateTime = LocalDateTime.of(2022, 4, 20, 10, 20, 20),
            comment = null,
            isScheduled = false
        ),
        isLoading = false
    )
) {

    override fun onEvent(event: TransactionManageEvent) {
        /**nope**/
    }
}
