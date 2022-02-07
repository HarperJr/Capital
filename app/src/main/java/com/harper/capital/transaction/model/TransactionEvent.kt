package com.harper.capital.transaction.model

import com.harper.capital.domain.model.Account

sealed class TransactionEvent {

    class TabSelect(val tabIndex: Int) : TransactionEvent()

    class AssetSourceSelect(
        val transactionType: TransactionType,
        val section: DataSetSection,
        val account: Account
    ) : TransactionEvent()

    class NewSourceClick(val transactionType: TransactionType, val dataSetType: DataSetType) :
        TransactionEvent()

    object BackClick : TransactionEvent()

    object Apply : TransactionEvent()
}
