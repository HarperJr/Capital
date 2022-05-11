package com.harper.capital.transaction.model

import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.AccountType
import com.harper.capital.domain.model.Contact

sealed class TransactionEvent {

    class TabSelect(val tabIndex: Int) : TransactionEvent()

    class AssetSourceSelect(
        val transactionType: TransactionType,
        val section: DataSetSection,
        val account: Account
    ) : TransactionEvent()

    class NewSourceClick(val transactionType: TransactionType, val type: AccountType) :
        TransactionEvent()

    object BackClick : TransactionEvent()
}
