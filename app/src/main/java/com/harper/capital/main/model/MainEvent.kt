package com.harper.capital.main.model

import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.TransferTransaction

sealed class MainEvent {

    class EditClick(val account: Account) : MainEvent()

    class IncomeClick(val account: Account?) : MainEvent()

    class ExpenseClick(val account: Account?) : MainEvent()

    class HistoryClick(val account: Account?) : MainEvent()

    class ActionCardClick(val type: ActionCardType) : MainEvent()

    class FavoriteTransferTransactionClick(val transaction: TransferTransaction) : MainEvent()

    object NewAssetClick : MainEvent()

    object SettingsClick : MainEvent()

    object AllOperationsClick : MainEvent()
}
