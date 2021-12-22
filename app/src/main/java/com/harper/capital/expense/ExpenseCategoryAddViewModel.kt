package com.harper.capital.expense

import com.harper.capital.expense.model.ExpenseAddEvent
import com.harper.capital.expense.model.ExpenseCategoryAddState
import com.harper.core.ui.ComponentViewModel
import com.harper.core.ui.EventObserver

class ExpenseCategoryAddViewModel : ComponentViewModel<ExpenseCategoryAddState>(
    defaultState = ExpenseCategoryAddState()
), EventObserver<ExpenseAddEvent> {

    override fun onEvent(event: ExpenseAddEvent) {

    }
}
