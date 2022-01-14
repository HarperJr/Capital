package com.harper.capital.category.model

import androidx.compose.runtime.Composable
import com.harper.capital.bottomsheet.IconsBottomSheetData
import com.harper.capital.domain.model.Currency
import com.harper.capital.domain.model.ExpenseIcon
import com.harper.capital.ext.getImageVector

sealed class CategoryManageBottomSheet {

    class Icons(private val selectedIcon: ExpenseIcon) : CategoryManageBottomSheet() {
        val data: IconsBottomSheetData
            @Composable
            get() = IconsBottomSheetData(
                icons = ExpenseIcon.values().map {
                    IconsBottomSheetData.Icon(it.name, it.getImageVector())
                },
                selectedIcon = selectedIcon.name
            )
    }

    class Currencies(val currencies: List<Currency>, val selectedCurrency: Currency) : CategoryManageBottomSheet()
}