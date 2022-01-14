package com.harper.capital.category

import com.harper.capital.category.model.CategoryManageEvent
import com.harper.capital.category.model.ExpenseCategoryAddState
import com.harper.core.ui.ComponentViewModel
import com.harper.core.ui.EventObserver

class CategoryManageViewModel : ComponentViewModel<ExpenseCategoryAddState>(
    defaultState = ExpenseCategoryAddState()
), EventObserver<CategoryManageEvent> {

    override fun onEvent(event: CategoryManageEvent) {

    }
}
