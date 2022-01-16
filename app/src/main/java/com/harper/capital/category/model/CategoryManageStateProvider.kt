package com.harper.capital.category.model

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class CategoryManageStateProvider : PreviewParameterProvider<CategoryManageState> {
    override val values: Sequence<CategoryManageState> =
        sequenceOf(
            CategoryManageState(selectedPage = 0)
        )
}
