package com.harper.capital.overview.model

sealed class OverviewEvent {

    class IncomeClick(val assetId: Long) : OverviewEvent()

    class ExpenseClick(val assetId: Long) : OverviewEvent()

    object AddAssetClick : OverviewEvent()
}
