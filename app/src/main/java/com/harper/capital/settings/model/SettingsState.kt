package com.harper.capital.settings.model

import com.harper.capital.domain.model.Currency
import com.harper.capital.ui.model.ColorTheme

data class SettingsState(
    val email: String = "capitaluser@capital.com",
    val colorTheme: ColorTheme = ColorTheme.SYSTEM,
    val currency: Currency = Currency.RUB,
    val isNotificationsEnabled: Boolean = false,
    val bottomSheetState: SettingsBottomSheetState = SettingsBottomSheetState(isExpended = false)
)

data class SettingsBottomSheetState(
    val bottomSheet: SettingsBottomSheet? = null,
    val isExpended: Boolean = true
)
