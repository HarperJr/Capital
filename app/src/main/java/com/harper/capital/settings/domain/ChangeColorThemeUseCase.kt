package com.harper.capital.settings.domain

import com.harper.capital.domain.model.ColorTheme
import com.harper.capital.prefs.SettingsManager

class ChangeColorThemeUseCase(private val settingsManager: SettingsManager) {

    suspend operator fun invoke(colorTheme: ColorTheme) =
        settingsManager.updateColorTheme(colorTheme)
}
