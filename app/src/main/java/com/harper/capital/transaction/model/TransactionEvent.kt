package com.harper.capital.transaction.model

import com.harper.capital.domain.model.Asset

sealed class TransactionEvent {

    class TabSelect(val tabIndex: Int) : TransactionEvent()

    class AssetSourceSelect(val section: DataSetSection, val asset: Asset) : TransactionEvent()

    class NewSourceClick(val pageIndex: Int, val section: DataSetSection) : TransactionEvent()

    object BackClick : TransactionEvent()
}
