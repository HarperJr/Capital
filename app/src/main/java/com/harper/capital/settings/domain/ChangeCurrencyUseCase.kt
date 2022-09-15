package com.harper.capital.settings.domain

import com.harper.capital.domain.model.Currency
import com.harper.capital.prefs.SettingsManager

class ChangeCurrencyUseCase(private val settingsManager: SettingsManager) {

    suspend operator fun invoke(currency: Currency) =
        settingsManager.updateCurrency(currency)
}
