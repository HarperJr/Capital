package com.harper.capital.overview.ui.model

sealed class OverviewEvent {

    class IncomeClick(val assetId: Long) : OverviewEvent()

    class ExpenseClick(val assetId: Long) : OverviewEvent()

    object AddAssetClick : OverviewEvent()
}
