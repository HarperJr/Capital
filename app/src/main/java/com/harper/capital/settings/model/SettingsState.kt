package com.harper.capital.settings.model

import com.harper.capital.domain.model.Currency

data class SettingsState(
    val email: String = "",
    val colorTheme: ColorTheme = ColorTheme.LIGHT,
    val currency: Currency = Currency.RUB,
    val isNotificationsEnabled: Boolean = false,
    val bottomSheetState: SettingsBottomSheetState = SettingsBottomSheetState(isExpended = false)
)

data class SettingsBottomSheetState(
    val bottomSheet: SettingsBottomSheet? = null,
    val isExpended: Boolean = true
)
