package com.harper.capital.category.model

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class CategoryManageStateProvider : PreviewParameterProvider<ExpenseCategoryAddState> {
    override val values: Sequence<ExpenseCategoryAddState> =
        sequenceOf(
            ExpenseCategoryAddState()
        )
}
