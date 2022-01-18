package com.harper.capital.settings.model

import com.harper.capital.domain.model.Currency

sealed class SettingsEvent {

    class ColorThemeSelect(val colorThemeName: String) : SettingsEvent()

    class CurrencySelect(val currency: Currency) : SettingsEvent()

    object BackClick : SettingsEvent()

    object ColorThemeSelectClick : SettingsEvent()

    object CurrencySelectClick : SettingsEvent()
}
