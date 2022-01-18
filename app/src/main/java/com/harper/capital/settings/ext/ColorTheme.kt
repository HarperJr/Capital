package com.harper.capital.settings.ext

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.harper.capital.R
import com.harper.capital.settings.model.ColorTheme

@Composable
fun ColorTheme.resolveText() = when (this) {
    ColorTheme.LIGHT -> stringResource(id = R.string.light)
    ColorTheme.DARK -> stringResource(id = R.string.dark)
    ColorTheme.SYSTEM -> stringResource(id = R.string.system)
}
