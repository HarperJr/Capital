package com.harper.capital.expense.model

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class ExpenseCategoryAddStateProvider : PreviewParameterProvider<ExpenseCategoryAddState> {
    override val values: Sequence<ExpenseCategoryAddState> =
        sequenceOf(
            ExpenseCategoryAddState()
        )
}
