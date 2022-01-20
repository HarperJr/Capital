package com.harper.capital.settings.domain

import com.harper.capital.ui.ColorThemeProvider
import com.harper.capital.ui.model.ColorTheme

class ChangeColorThemeUseCase(private val colorThemeProvider: ColorThemeProvider) {

    operator fun invoke(colorTheme: ColorTheme) {
        colorThemeProvider.colorTheme = colorTheme.name
    }
}
