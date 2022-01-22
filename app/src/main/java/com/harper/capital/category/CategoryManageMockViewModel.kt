package com.harper.capital.category

import com.harper.capital.category.model.CategoryManageEvent
import com.harper.capital.category.model.CategoryManageState
import com.harper.core.ui.ComponentViewModel
import com.harper.core.ui.EventObserver

class CategoryManageMockViewModel : ComponentViewModel<CategoryManageState>(
    defaultState = CategoryManageState(selectedPage = 0)
), EventObserver<CategoryManageEvent> {

    override fun onEvent(event: CategoryManageEvent) {
        /**nope**/
    }
}
