package com.harper.capital.analytics

import androidx.compose.ui.unit.dp
import com.harper.capital.analytics.domain.model.BalancePartitions
import com.harper.capital.analytics.model.AnalyticsDataSet
import com.harper.capital.analytics.model.AnalyticsType
import com.harper.capital.analytics.model.Chart
import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.AccountType
import com.harper.capital.ext.accountBackgroundColor
import com.harper.core.component.chart.LineChartData
import com.harper.core.ext.orElse
import com.harper.core.theme.CapitalColors
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AnalyticsDataSetFactory {
    private val monthFormatter = DateTimeFormatter.ofPattern("LLL")

    fun create(
        type: AnalyticsType,
        accounts: List<Account>,
        partitions: BalancePartitions
    ): AnalyticsDataSet = when (type) {
        AnalyticsType.BALANCE -> createBalancesDataSet(accounts, partitions)
        AnalyticsType.INCOME_LIABILITY -> createIncomesLiabilitiesDataSet(accounts, partitions)
        AnalyticsType.INCOME -> createIncomesDataSet(accounts)
        AnalyticsType.LIABILITY -> createLiabilitiesDataSet(accounts)
    }

    private fun createBalancesDataSet(accounts: List<Account>, partitions: BalancePartitions): AnalyticsDataSet {
        val assets = accounts.filter { it.type == AccountType.ASSET }
        val balanceAccumulatedValues = mutableMapOf<LocalDate, Double>()
        val chartLines = assets.map { account ->
            val partitionsByAccount = partitions.balanceEntries[account.id]?.map {
                it.copy(balance = it.balance + balanceAccumulatedValues[it.period].orElse(0.0))
                    .also { partition ->
                        balanceAccumulatedValues[it.period] = partition.balance
                    }
            }
            LineChartData.Line(
                points = partitionsByAccount?.map { it.balance.toFloat() }.orEmpty(),
                color = accountBackgroundColor(account.color)
            )
        }
        val labels = partitions.periods.map { it.format(monthFormatter) }
        return AnalyticsDataSet(
            accounts = assets,
            chart = Chart.LineChart(
                data = LineChartData(
                    lines = chartLines,
                    labels = labels,
                    thickness = 1.dp,
                    fillLines = true,
                    startAtZero = true
                )
            )
        )
    }

    private fun createIncomesLiabilitiesDataSet(accounts: List<Account>, partitions: BalancePartitions): AnalyticsDataSet {
        val assets = accounts.filter { it.type == AccountType.ASSET }
        val liabilities = accounts.filter { it.type == AccountType.LIABILITY }

        val assetIds = assets.map { it.id }
        val incomePartitions = partitions.balanceEntries.filterKeys { assetIds.contains(it) }
        val balanceAccumulatedValues = mutableMapOf<LocalDate, Double>()
        incomePartitions.forEach { (_, partitions) ->
            partitions.forEach {
                balanceAccumulatedValues[it.period] = it.balance + balanceAccumulatedValues[it.period].orElse(0.0)
            }
        }
        val incomeChartLine = LineChartData.Line(
            points = balanceAccumulatedValues.map { it.value.toFloat() },
            color = CapitalColors.Blue
        )

        val liabilityIds = liabilities.map { it.id }
        val liabilityPartitions = partitions.balanceEntries.filterKeys { liabilityIds.contains(it) }
        val liabilityAccumulatedValues = mutableMapOf<LocalDate, Double>()
        liabilityPartitions.forEach { (_, partitions) ->
            partitions.forEach {
                liabilityAccumulatedValues[it.period] = it.balance + liabilityAccumulatedValues[it.period].orElse(0.0)
            }
        }
        val liabilityChartLine = LineChartData.Line(
            points = liabilityAccumulatedValues.map { it.value.toFloat() },
            color = CapitalColors.RedError
        )

        val labels = partitions.periods.map { it.format(monthFormatter) }
        return AnalyticsDataSet(
            accounts = assets + liabilities,
            chart = Chart.LineChart(
                data = LineChartData(
                    lines = listOf(incomeChartLine, liabilityChartLine),
                    labels = labels,
                    thickness = 3.dp,
                    fillLines = false,
                    startAtZero = true
                )
            )
        )
    }

    private fun createIncomesDataSet(accounts: List<Account>): AnalyticsDataSet {
        val assets = accounts.filter { it.type == AccountType.INCOME }
        return AnalyticsDataSet(
            accounts = assets,
            chart = Chart.PieLoadingChart
        )
    }

    private fun createLiabilitiesDataSet(accounts: List<Account>): AnalyticsDataSet {
        val assets = accounts.filter { it.type == AccountType.LIABILITY }
        return AnalyticsDataSet(
            accounts = assets,
            chart = Chart.PieLoadingChart
        )
    }
}
