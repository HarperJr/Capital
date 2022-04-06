package com.harper.capital.analytics.model

import com.harper.core.component.chart.line.LineChartData
import com.harper.core.component.chart.pie.PieChartData

sealed class Chart {

    object LineLoadingChart : Chart()

    object PieLoadingChart : Chart()

    class LineChart(val data: LineChartData) : Chart()

    class PieChart(val data: PieChartData) : Chart()
}
