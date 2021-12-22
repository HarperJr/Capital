package com.harper.capital.expense.model

import com.harper.capital.domain.model.Currency

sealed class ExpenseAddEvent {

    object CurrencySelectClick : ExpenseAddEvent()

    class CurrencySelect(val currency: Currency) : ExpenseAddEvent()
}