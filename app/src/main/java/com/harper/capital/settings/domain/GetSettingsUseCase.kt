package com.harper.capital.settings.domain

import com.harper.capital.domain.model.Settings
import com.harper.capital.prefs.SettingsManager

class GetSettingsUseCase(private val settingsManager: SettingsManager) {

    suspend operator fun invoke(): Settings = settingsManager.provide()
}
