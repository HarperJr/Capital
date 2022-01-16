package com.harper.capital.ext

import androidx.compose.runtime.Composable
import com.harper.capital.domain.model.CategoryIcon
import com.harper.core.theme.CapitalIcons

@Composable
fun CategoryIcon.getImageVector() = when (this) {
    CategoryIcon.MOBILE -> CapitalIcons.Mobile
    CategoryIcon.PRODUCTS -> CapitalIcons.ProductCart
}
