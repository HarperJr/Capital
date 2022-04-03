package com.harper.capital.main.model

import com.harper.capital.domain.model.Account

sealed class MainEvent {

    class EditClick(val account: Account) : MainEvent()

    class IncomeClick(val account: Account?) : MainEvent()

    class ExpenseClick(val account: Account?) : MainEvent()

    class HistoryClick(val account: Account?) : MainEvent()

    class ActionCardClick(val id: Int) : MainEvent()

    object NewAssetClick : MainEvent()

    object SettingsClick : MainEvent()
}
