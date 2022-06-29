package com.harper.capital.transaction.manage.model

import com.harper.capital.domain.model.Account
import java.time.LocalDate

sealed class TransactionManageEvent {

    class SourceAmountChange(val amount: Double) : TransactionManageEvent()

    class TargetAmountChange(val amount: Double) : TransactionManageEvent()

    class DateSelect(val date: LocalDate) : TransactionManageEvent()

    class CommentChange(val comment: String) : TransactionManageEvent()

    class ScheduledCheckChange(val isChecked: Boolean) : TransactionManageEvent()

    class Init(val source: Account, val receiver: Account) : TransactionManageEvent()

    object BackClick : TransactionManageEvent()

    object Apply : TransactionManageEvent()
}
