package com.harper.capital.main

import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.AccountColor
import com.harper.capital.domain.model.AccountIcon
import com.harper.capital.domain.model.AccountMetadata
import com.harper.capital.domain.model.AccountType
import com.harper.capital.domain.model.Currency
import com.harper.capital.main.domain.model.Summary
import com.harper.capital.main.model.MainEvent
import com.harper.capital.main.model.MainState
import com.harper.core.ui.ComponentViewModel

class MainMockViewModel(isLoading: Boolean = false) : ComponentViewModel<MainState, MainEvent>(
    initialState = MainState(
        summary = Summary(expenses = 14241.24, balance = 10000.0, Currency.RUB),
        accounts = listOf(
            Account(
                0L,
                "Tinkoff Credit",
                type = AccountType.ASSET,
                color = AccountColor.TINKOFF,
                icon = AccountIcon.TINKOFF,
                Currency.RUB,
                2044.44,
                metadata = AccountMetadata.Loan(limit = 40000.00)
            ),
            Account(
                1L,
                "Tinkoff USD",
                type = AccountType.ASSET,
                color = AccountColor.TINKOFF,
                icon = AccountIcon.TINKOFF,
                Currency.USD,
                24.44,
                metadata = AccountMetadata.Goal(goal = 100000.00)
            )
        ),
        isLoading = isLoading,
    )
) {

    override fun onEvent(event: MainEvent) {
        /**nope**/
    }
}
