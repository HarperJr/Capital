package com.harper.capital.settings.domain

import com.harper.capital.domain.model.Settings
import com.harper.capital.prefs.SettingsManager
import kotlinx.coroutines.flow.Flow

class FetchSettingsUseCase(private val settingsManager: SettingsManager) {

    operator fun invoke(): Flow<Settings> = settingsManager.asFlow
}
