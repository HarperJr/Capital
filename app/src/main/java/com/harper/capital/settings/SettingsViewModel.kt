package com.harper.capital.settings

import com.harper.capital.domain.model.Currency
import com.harper.capital.navigation.GlobalRouter
import com.harper.capital.settings.domain.ChangeColorThemeUseCase
import com.harper.capital.settings.domain.GetColorThemeUseCase
import com.harper.capital.settings.model.SettingsBottomSheet
import com.harper.capital.settings.model.SettingsBottomSheetState
import com.harper.capital.settings.model.SettingsEvent
import com.harper.capital.settings.model.SettingsState
import com.harper.capital.ui.model.ColorTheme
import com.harper.core.ui.ComponentViewModel

class SettingsViewModel(
    private val router: GlobalRouter,
    private val changeColorThemeUseCase: ChangeColorThemeUseCase,
    private val getColorThemeUseCase: GetColorThemeUseCase
) : ComponentViewModel<SettingsState, SettingsEvent>(
    initialState = SettingsState(colorTheme = getColorThemeUseCase())
) {

    override fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.CurrencySelect -> onCurrencySelect(event)
            is SettingsEvent.ColorThemeSelect -> onColorThemeSelect(event)
            SettingsEvent.ColorThemeSelectClick -> onColorThemeSelectClick()
            SettingsEvent.CurrencySelectClick -> onCurrencySelectClick()
            SettingsEvent.BackClick -> router.back()
        }
    }

    private fun onCurrencySelect(event: SettingsEvent.CurrencySelect) {
        update {
            it.copy(currency = event.currency, bottomSheetState = it.bottomSheetState.copy(isExpended = false))
        }
    }

    private fun onColorThemeSelect(event: SettingsEvent.ColorThemeSelect) {
        update {
            val selectedColorTheme = ColorTheme.valueOf(event.colorThemeName)
            it.copy(colorTheme = selectedColorTheme, bottomSheetState = it.bottomSheetState.copy(isExpended = false))
        }
        changeColorThemeUseCase(state.value.colorTheme)
    }

    private fun onColorThemeSelectClick() {
        update {
            it.copy(
                bottomSheetState = SettingsBottomSheetState(
                    bottomSheet = SettingsBottomSheet.ColorThemes(
                        selectedColorTheme = it.colorTheme
                    )
                )
            )
        }
    }

    private fun onCurrencySelectClick() {
        update {
            it.copy(
                bottomSheetState = SettingsBottomSheetState(
                    bottomSheet = SettingsBottomSheet.Currencies(
                        currencies = Currency.values().toList(),
                        selectedCurrency = it.currency
                    )
                )
            )
        }
    }
}
