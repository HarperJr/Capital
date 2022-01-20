package com.harper.capital.settings.domain

import com.harper.capital.ui.ColorThemeProvider
import com.harper.capital.ui.model.ColorTheme

class GetColorThemeUseCase(private val colorThemeProvider: ColorThemeProvider) {

    operator fun invoke(): ColorTheme = ColorTheme.valueOf(colorThemeProvider.colorTheme)
}
