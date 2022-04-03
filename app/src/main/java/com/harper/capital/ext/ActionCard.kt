package com.harper.capital.ext

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.harper.capital.R
import com.harper.capital.main.model.ActionCardType

@Composable
fun ActionCardType.resolveTitle(): String = when (this) {
    ActionCardType.ANALYTICS -> stringResource(id = R.string.analytics)
}
