package com.harper.capital.analytics.model

import com.harper.core.component.chart.LineChartData

sealed class Chart {

    object LineLoadingChart : Chart()

    object PieLoadingChart : Chart()

    class LineChart(val data: LineChartData) : Chart()
}
