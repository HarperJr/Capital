package com.harper.capital.transaction.manage.model

sealed class TransactionManageEvent {

    object BackClick : TransactionManageEvent()
}
