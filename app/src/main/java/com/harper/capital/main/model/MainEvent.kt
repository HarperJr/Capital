package com.harper.capital.main.model

import com.harper.capital.domain.model.Asset

sealed class MainEvent {

    class EditClick(val asset: Asset) : MainEvent()

    class IncomeClick(val asset: Asset?) : MainEvent()

    class ExpenseClick(val asset: Asset?) : MainEvent()

    class HistoryClick(val asset: Asset?) : MainEvent()

    object NewAssetClick : MainEvent()
}
