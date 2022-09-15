package com.harper.capital.settings.domain

import com.harper.capital.domain.model.AccountPresentation
import com.harper.capital.prefs.SettingsManager

class ChangeAccountPresentationUseCase(private val settingsManager: SettingsManager) {

    suspend operator fun invoke(accountPresentation: AccountPresentation) =
        settingsManager.updateAccountPresentation(accountPresentation)
}
