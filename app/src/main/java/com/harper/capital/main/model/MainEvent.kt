package com.harper.capital.main.model

sealed class MainEvent {

    class IncomeClick(val assetId: Long) : MainEvent()

    class ExpenseClick(val assetId: Long) : MainEvent()

    object AddAssetClick : MainEvent()
}
