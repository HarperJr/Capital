package com.harper.capital.settings.domain

import com.harper.capital.domain.model.ColorTheme
import com.harper.capital.prefs.SettingsProvider

class ChangeColorThemeUseCase(private val settingsProvider: SettingsProvider) {

    suspend operator fun invoke(colorTheme: ColorTheme) =
        settingsProvider.updateColorTheme(colorTheme)
}
