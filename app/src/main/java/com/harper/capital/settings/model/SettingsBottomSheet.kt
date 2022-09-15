package com.harper.capital.settings.model

import androidx.compose.runtime.Composable
import com.harper.capital.bottomsheet.SelectorBottomSheetData
import com.harper.capital.domain.model.AccountPresentation
import com.harper.capital.domain.model.ColorTheme
import com.harper.capital.domain.model.Currency
import com.harper.capital.settings.ext.resolveText

sealed class SettingsBottomSheet {

    class ColorThemes(private val selectedColorTheme: ColorTheme) : SettingsBottomSheet() {
        val data: SelectorBottomSheetData
            @Composable
            get() = SelectorBottomSheetData(
                values = ColorTheme.values().map {
                    SelectorBottomSheetData.Value(it.name, it.resolveText())
                },
                selectedValue = selectedColorTheme.name
            )
    }

    class AccountPresentations(private val selectedAccountPresentation: AccountPresentation) : SettingsBottomSheet() {
        val data: SelectorBottomSheetData
            @Composable
            get() = SelectorBottomSheetData(
                values = AccountPresentation.values().map {
                    SelectorBottomSheetData.Value(it.name, it.resolveText())
                },
                selectedValue = selectedAccountPresentation.name
            )
    }

    class Currencies(val currencies: List<Currency>, val selectedCurrency: Currency) : SettingsBottomSheet()
}
