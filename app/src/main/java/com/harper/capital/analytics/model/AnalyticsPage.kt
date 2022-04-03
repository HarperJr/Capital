package com.harper.capital.analytics.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.harper.capital.R

data class AnalyticsPage(val type: AnalyticsType, val dataSet: AnalyticsDataSet) {
    val title: String
        @Composable
        get() = stringResource(id = type.resolveTitleRes())
}

private fun AnalyticsType.resolveTitleRes(): Int = when (this) {
    AnalyticsType.INCOME_LIABILITY -> R.string.income_liability
    AnalyticsType.BALANCE -> R.string.balance
    AnalyticsType.INCOME -> R.string.income
    AnalyticsType.LIABILITY -> R.string.liability
}
