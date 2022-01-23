package com.harper.capital.transaction.manage

import com.harper.capital.domain.model.Asset
import com.harper.capital.domain.model.AssetColor
import com.harper.capital.domain.model.AssetIcon
import com.harper.capital.domain.model.AssetMetadata
import com.harper.capital.domain.model.Currency
import com.harper.capital.transaction.manage.model.AssetPair
import com.harper.capital.transaction.manage.model.TransactionManageEvent
import com.harper.capital.transaction.manage.model.TransactionManageState
import com.harper.capital.transaction.model.TransactionType
import com.harper.core.ui.ComponentViewModel
import com.harper.core.ui.EventObserver

class TransactionManageMockViewModel : ComponentViewModel<TransactionManageState>(
    defaultState = TransactionManageState(
        transactionType = TransactionType.EXPENSE,
        assetPair = AssetPair(
            Asset(
                id = 0L,
                name = "Tinkoff",
                amount = 1000.0,
                currency = Currency.RUB,
                color = AssetColor.TINKOFF,
                icon = AssetIcon.TINKOFF,
                metadata = AssetMetadata.Debet
            ),
            Asset(
                id = 0L,
                name = "Products",
                amount = 1000.0,
                currency = Currency.RUB,
                color = AssetColor.CATEGORY,
                icon = AssetIcon.PRODUCTS,
                metadata = AssetMetadata.Expense
            )
        ),
        isLoading = false
    )
), EventObserver<TransactionManageEvent> {

    override fun onEvent(event: TransactionManageEvent) {
        /**nope**/
    }
}
