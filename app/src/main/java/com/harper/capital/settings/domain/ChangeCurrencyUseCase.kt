package com.harper.capital.settings.domain

import com.harper.capital.domain.model.Currency
import com.harper.capital.prefs.SettingsProvider

class ChangeCurrencyUseCase(private val settingsProvider: SettingsProvider) {

    suspend operator fun invoke(currency: Currency) =
        settingsProvider.updateCurrency(currency)
}
