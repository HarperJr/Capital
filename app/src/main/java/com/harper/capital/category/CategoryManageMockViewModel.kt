package com.harper.capital.category

import com.harper.capital.category.model.CategoryManageEvent
import com.harper.capital.category.model.CategoryManageState
import com.harper.core.ui.ComponentViewModel

class CategoryManageMockViewModel : ComponentViewModel<CategoryManageState, CategoryManageEvent>(
    initialState = CategoryManageState(selectedPage = 0)
) {

    override fun onEvent(event: CategoryManageEvent) {
        /**nope**/
    }
}
