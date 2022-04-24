package com.harper.capital.settings.domain

import com.harper.capital.domain.model.Settings
import com.harper.capital.prefs.SettingsProvider

class GetSettingsUseCase(private val settingsProvider: SettingsProvider) {

    suspend operator fun invoke(): Settings = settingsProvider.provide()
}
