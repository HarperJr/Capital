package com.harper.capital.accounts.model

sealed class AccountsEvent {

    class NewSourceClick(val section: DataSetSection) : AccountsEvent()

    class SourceClick(val section: DataSetSection, val id: Long) : AccountsEvent()

    object BackClick : AccountsEvent()
}
