package com.harper.capital.settings.model

import com.harper.capital.domain.model.AccountPresentation
import com.harper.capital.domain.model.ColorTheme
import com.harper.capital.domain.model.Currency

data class SettingsState(
    val email: String = "capitaluser@capital.com",
    val colorTheme: ColorTheme = ColorTheme.SYSTEM,
    val accountPresentation: AccountPresentation = AccountPresentation.CAROUSEL,
    val currency: Currency = Currency.RUB,
    val isNotificationsEnabled: Boolean = false,
    val bottomSheetState: SettingsBottomSheetState = SettingsBottomSheetState(isExpanded = false)
)

data class SettingsBottomSheetState(
    val bottomSheet: SettingsBottomSheet? = null,
    val isExpanded: Boolean = true
)
