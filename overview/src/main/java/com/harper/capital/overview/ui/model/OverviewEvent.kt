package com.harper.capital.overview.ui.model

sealed class OverviewEvent {

    data class IncomeClick(val assetId: Long) : OverviewEvent()

    data class ExpenseClick(val assetId: Long) : OverviewEvent()

    object AddAssetClick : OverviewEvent()
}
