package com.harper.capital.ext

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.harper.capital.R
import com.harper.capital.main.model.ActionCardType

@Composable
fun ActionCardType.resolveTitle(): String = when (this) {
    ActionCardType.ACCOUNTS -> stringResource(id = R.string.accounts)
    ActionCardType.ANALYTICS_BALANCE -> stringResource(id = R.string.analytics_balance)
    ActionCardType.ANALYTICS_INCOME -> stringResource(id = R.string.analytics_income)
    ActionCardType.ANALYTICS_INCOME_LIABILITY -> stringResource(id = R.string.analytics_income_liability)
    ActionCardType.ANALYTICS_LIABILITY -> stringResource(id = R.string.analytics_liability)
}
