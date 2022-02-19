package com.harper.capital.transaction.manage.model

import java.time.LocalDate

sealed class TransactionManageEvent {

    class AmountChange(val amount: Double) : TransactionManageEvent()

    class DateSelectClick(val date: LocalDate) : TransactionManageEvent()

    class DateSelect(val date: LocalDate) : TransactionManageEvent()

    class CommentChange(val comment: String) : TransactionManageEvent()

    class ScheduledCheckChange(val isChecked: Boolean) : TransactionManageEvent()

    object BackClick : TransactionManageEvent()

    object Apply : TransactionManageEvent()

    object HideDialog : TransactionManageEvent()
}
