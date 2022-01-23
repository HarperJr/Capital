package com.harper.capital.transaction.model

import com.harper.capital.domain.model.Asset

sealed class TransactionEvent {

    class TabSelect(val tabIndex: Int) : TransactionEvent()

    class AssetSourceSelect(
        val transactionType: TransactionType,
        val section: DataSetSection,
        val asset: Asset
    ) : TransactionEvent()

    class NewSourceClick(val transactionType: TransactionType, val dataSetType: DataSetType) :
        TransactionEvent()

    object BackClick : TransactionEvent()
}
