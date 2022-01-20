package com.harper.capital.settings.model

import androidx.compose.runtime.Composable
import com.harper.capital.bottomsheet.SelectorBottomSheetData
import com.harper.capital.domain.model.Currency
import com.harper.capital.settings.ext.resolveText
import com.harper.capital.ui.model.ColorTheme

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

    class Currencies(val currencies: List<Currency>, val selectedCurrency: Currency) : SettingsBottomSheet()
}
