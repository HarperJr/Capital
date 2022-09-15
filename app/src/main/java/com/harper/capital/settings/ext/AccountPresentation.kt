package com.harper.capital.settings.ext

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.harper.capital.R
import com.harper.capital.domain.model.AccountPresentation

@Composable
fun AccountPresentation.resolveText() = when (this) {
    AccountPresentation.LIST -> stringResource(id = R.string.list)
    AccountPresentation.CAROUSEL -> stringResource(id = R.string.carousel)
}
