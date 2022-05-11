package com.harper.capital.settings

import com.harper.capital.domain.model.ColorTheme
import com.harper.capital.domain.model.Currency
import com.harper.capital.navigation.GlobalRouter
import com.harper.capital.settings.domain.ChangeColorThemeUseCase
import com.harper.capital.settings.domain.ChangeCurrencyUseCase
import com.harper.capital.settings.domain.GetSettingsUseCase
import com.harper.capital.settings.model.SettingsBottomSheet
import com.harper.capital.settings.model.SettingsBottomSheetState
import com.harper.capital.settings.model.SettingsEvent
import com.harper.capital.settings.model.SettingsState
import com.harper.core.ui.ComponentViewModel
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val router: GlobalRouter,
    private val changeColorThemeUseCase: ChangeColorThemeUseCase,
    private val changeCurrencyUseCase: ChangeCurrencyUseCase,
    private val getSettingsUseCase: GetSettingsUseCase
) : ComponentViewModel<SettingsState, SettingsEvent>(
    initialState = SettingsState()
) {

    override fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.CurrencySelect -> onCurrencySelect(event)
            is SettingsEvent.ColorThemeSelect -> onColorThemeSelect(event)
            is SettingsEvent.ColorThemeSelectClick -> onColorThemeSelectClick()
            is SettingsEvent.CurrencySelectClick -> onCurrencySelectClick()
            is SettingsEvent.BackClick -> router.back()
        }
    }

    override fun onFirstComposition() {
        super.onFirstComposition()
        launch {
            val settings = getSettingsUseCase()
            update {
                it.copy(
                    colorTheme = settings.colorTheme,
                    currency = settings.currency
                )
            }
        }
    }

    private fun onCurrencySelect(event: SettingsEvent.CurrencySelect) {
        update {
            it.copy(
                currency = event.currency,
                bottomSheetState = it.bottomSheetState.copy(isExpanded = false)
            )
        }
        launch {
            changeCurrencyUseCase(event.currency)
        }
    }

    private fun onColorThemeSelect(event: SettingsEvent.ColorThemeSelect) {
        val selectedColorTheme = ColorTheme.valueOf(event.colorThemeName)
        update {
            it.copy(
                colorTheme = selectedColorTheme,
                bottomSheetState = it.bottomSheetState.copy(isExpanded = false)
            )
        }
        launch {
            changeColorThemeUseCase(selectedColorTheme)
        }
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
