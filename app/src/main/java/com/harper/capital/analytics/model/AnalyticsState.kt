package com.harper.capital.analytics.model

data class AnalyticsState(
    val selectedPage: Int,
    val isLoading: Boolean = true,
    val pages: List<AnalyticsPage> = emptyPages()
)

private fun emptyPages(): List<AnalyticsPage> = AnalyticsType.values()
    .map {
        AnalyticsPage(type = it, dataSet = AnalyticsDataSet(accounts = emptyList(), chart = Chart.LineLoadingChart))
    }
