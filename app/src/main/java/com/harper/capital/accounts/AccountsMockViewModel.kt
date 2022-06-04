package com.harper.capital.accounts

import com.harper.capital.accounts.model.AccountDataSet
import com.harper.capital.accounts.model.AccountsEvent
import com.harper.capital.accounts.model.AccountsState
import com.harper.capital.accounts.model.DataSetSection
import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.AccountColor
import com.harper.capital.domain.model.AccountIcon
import com.harper.capital.domain.model.AccountType
import com.harper.capital.domain.model.Currency
import com.harper.core.ui.ComponentViewModel

class AccountsMockViewModel : ComponentViewModel<AccountsState, AccountsEvent>(
    initialState = AccountsState(
        accountDataSets = mapOf(
            DataSetSection.ASSET to AccountDataSet(
                type = AccountType.ASSET,
                accounts = listOf(
                    Account(
                        0L,
                        "Tinkoff Black",
                        AccountType.ASSET,
                        AccountColor.TINKOFF,
                        AccountIcon.TINKOFF,
                        Currency.RUB,
                        1000.0,
                        isIncluded = true,
                        isArchived = false,
                        metadata = null
                    ),
                    Account(
                        1L,
                        "Alpha Credit",
                        AccountType.ASSET,
                        AccountColor.ALPHA,
                        AccountIcon.ALPHA,
                        Currency.RUB,
                        2455.0,
                        isIncluded = true,
                        isArchived = false,
                        metadata = null
                    )
                )
            )
        )
    )
) {

    override fun onEvent(event: AccountsEvent) {

    }
}
