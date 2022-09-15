package com.harper.capital.ext

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.harper.capital.R
import com.harper.capital.main.model.ActionCardType

@Composable
fun ActionCardType.resolveTitle(): String = when (this) {
    ActionCardType.ACCOUNTS -> stringResource(id = R.string.accounts)
    ActionCardType.ANALYTICS -> stringResource(id = R.string.analytics)
    ActionCardType.FAVORITE -> stringResource(id = R.string.favorite)
    ActionCardType.PLAN -> stringResource(id = R.string.plan)
}
