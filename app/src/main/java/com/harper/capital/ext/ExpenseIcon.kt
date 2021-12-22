package com.harper.capital.ext

import androidx.compose.runtime.Composable
import com.harper.capital.domain.model.ExpenseIcon
import com.harper.core.theme.CapitalIcons

@Composable
fun ExpenseIcon.getImageVector() = when (this) {
    ExpenseIcon.MOBILE -> CapitalIcons.Mobile
    ExpenseIcon.PRODUCTS -> CapitalIcons.ProductCart
}
