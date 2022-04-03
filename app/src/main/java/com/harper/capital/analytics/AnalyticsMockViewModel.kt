package com.harper.capital.analytics

import com.harper.capital.analytics.model.AnalyticsDataSet
import com.harper.capital.analytics.model.AnalyticsEvent
import com.harper.capital.analytics.model.AnalyticsPage
import com.harper.capital.analytics.model.AnalyticsState
import com.harper.capital.analytics.model.AnalyticsType
import com.harper.capital.analytics.model.Chart
import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.AccountColor
import com.harper.capital.domain.model.AccountIcon
import com.harper.capital.domain.model.AccountType
import com.harper.capital.domain.model.Currency
import com.harper.core.ui.ComponentViewModel

class AnalyticsMockViewModel : ComponentViewModel<AnalyticsState, AnalyticsEvent>(
    initialState = AnalyticsState(
        selectedPage = 0,
        isLoading = false,
        pages = listOf(
            AnalyticsPage(
                type = AnalyticsType.BALANCE,
                dataSet = AnalyticsDataSet(
                    chart = Chart.LineLoadingChart,
                    accounts = listOf(
                        Account(
                            id = 0L,
                            name = "Tinkoff Black",
                            type = AccountType.ASSET,
                            color = AccountColor.TINKOFF,
                            icon = AccountIcon.TINKOFF,
                            currency = Currency.RUB,
                            balance = 1000.0
                        ),
                        Account(
                            id = 0L,
                            name = "VTB Credit",
                            type = AccountType.ASSET,
                            color = AccountColor.VTB,
                            icon = AccountIcon.VTB,
                            currency = Currency.RUB,
                            balance = 2400.0
                        )
                    )
                )
            ),
            AnalyticsPage(
                type = AnalyticsType.INCOME_LIABILITY,
                dataSet = AnalyticsDataSet(
                    chart = Chart.LineLoadingChart,
                    accounts = emptyList()
                )
            ),
            AnalyticsPage(
                type = AnalyticsType.INCOME,
                dataSet = AnalyticsDataSet(
                    chart = Chart.LineLoadingChart,
                    accounts = emptyList()
                )
            ),
            AnalyticsPage(
                type = AnalyticsType.LIABILITY,
                dataSet = AnalyticsDataSet(
                    chart = Chart.LineLoadingChart,
                    accounts = emptyList()
                )
            )
        )
    )
) {

    override fun onEvent(event: AnalyticsEvent) {
        /**nope**/
    }
}
