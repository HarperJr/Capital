package com.harper.capital.transaction

import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.AccountColor
import com.harper.capital.domain.model.AccountIcon
import com.harper.capital.domain.model.AccountType
import com.harper.capital.domain.model.Currency
import com.harper.capital.transaction.model.AccountDataSet
import com.harper.capital.transaction.model.DataSetSection
import com.harper.capital.transaction.model.SelectableAccount
import com.harper.capital.transaction.model.TransactionEvent
import com.harper.capital.transaction.model.TransactionPage
import com.harper.capital.transaction.model.TransactionState
import com.harper.capital.transaction.model.TransactionType
import com.harper.core.ui.ComponentViewModel

class TransactionMockViewModel : ComponentViewModel<TransactionState, TransactionEvent>(
    initialState = TransactionState(
        selectedPage = 0,
        pages = listOf(
            TransactionPage(
                type = TransactionType.LIABILITY,
                accountDataSets = mapOf(
                    DataSetSection.FROM to AccountDataSet(
                        type = AccountType.ASSET,
                        accounts = listOf(
                            SelectableAccount(
                                isEnabled = true,
                                account = Account(
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
                                )
                            ),
                            SelectableAccount(
                                isEnabled = true,
                                account = Account(
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
                        ),
                        selectedAccountId = 0L
                    ),
                    DataSetSection.TO to AccountDataSet(
                        type = AccountType.ASSET,
                        accounts = listOf(
                            SelectableAccount(
                                isEnabled = true,
                                account = Account(
                                    0L,
                                    "Products",
                                    AccountType.LIABILITY,
                                    AccountColor.LIABILITY,
                                    AccountIcon.PRODUCTS,
                                    Currency.RUB,
                                    400.0,
                                    isIncluded = true,
                                    isArchived = false,
                                    metadata = null
                                )
                            )

                        ),
                        selectedAccountId = 0L
                    )
                )
            )
        )
    )
) {

    override fun onEvent(event: TransactionEvent) {
        /**nope**/
    }
}
