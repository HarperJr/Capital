package com.harper.capital.analytics

import androidx.compose.ui.unit.dp
import com.harper.capital.analytics.domain.model.BalancePartitions
import com.harper.capital.analytics.model.AnalyticsDataSet
import com.harper.capital.analytics.model.AnalyticsType
import com.harper.capital.analytics.model.Chart
import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.AccountType
import com.harper.capital.domain.model.BalancePartition
import com.harper.capital.ext.accountBackgroundColor
import com.harper.core.component.chart.line.LineChartData
import com.harper.core.component.chart.pie.PieChartData
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
        AnalyticsType.INCOME -> createIncomesDataSet(accounts, partitions)
        AnalyticsType.LIABILITY -> createLiabilitiesDataSet(accounts, partitions)
    }

    private fun createBalancesDataSet(accounts: List<Account>, partitions: BalancePartitions): AnalyticsDataSet {
        val assets = accounts.filter { it.type == AccountType.ASSET }
        val balanceAccumulatedValues = mutableMapOf<LocalDate, Double>()
        val chartLines = assets.map { asset ->
            val partitionsByAccount = partitions.entries[asset.id]
                ?.map {
                    it.copy(balance = it.balance + balanceAccumulatedValues[it.period].orElse(0.0))
                        .also { partition ->
                            balanceAccumulatedValues[it.period] = partition.balance
                        }
                }
                .orElse(partitions.periods.map { BalancePartition(asset.id, balance = 0.0, period = it) })
            LineChartData.Line(
                points = partitionsByAccount.map { it.balance.toFloat() },
                color = accountBackgroundColor(asset.color)
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
        val incomes = accounts.filter { it.type == AccountType.INCOME }
        val liabilities = accounts.filter { it.type == AccountType.LIABILITY }

        val incomeChartLine = LineChartData.Line(
            points = calculateValuesForAccountIds(partitions, ids = incomes.map { it.id }),
            color = CapitalColors.Blue
        )
        val liabilityChartLine = LineChartData.Line(
            points = calculateValuesForAccountIds(partitions, ids = liabilities.map { it.id }),
            color = CapitalColors.RedError
        )

        val labels = partitions.periods.map { it.format(monthFormatter) }
        return AnalyticsDataSet(
            accounts = incomes + liabilities,
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

    private fun createIncomesDataSet(accounts: List<Account>, partitions: BalancePartitions): AnalyticsDataSet {
        val incomes = accounts.filter { it.type == AccountType.INCOME }
        var pieSlices = incomes.map { income ->
            val partition = partitions.entries[income.id]
                ?.firstOrNull()
                .orElse(BalancePartition(income.id, balance = 0.0, LocalDate.now())) // TODO remove after testing
            PieChartData.Slice(value = partition.balance.toFloat(), color = accountBackgroundColor(income.color))
        }
        if (pieSlices.isEmpty()) {
            pieSlices = listOf(PieChartData.Slice(value = 1f, color = CapitalColors.GreyMedium))
        }
        return AnalyticsDataSet(
            accounts = incomes,
            chart = Chart.PieChart(
                data = PieChartData(pieSlices)
            )
        )
    }

    private fun createLiabilitiesDataSet(accounts: List<Account>, partitions: BalancePartitions): AnalyticsDataSet {
        val liabilities = accounts.filter { it.type == AccountType.LIABILITY }
        var pieSlices = liabilities.map { liability ->
            val partition = partitions.entries[liability.id]
                ?.firstOrNull()
                .orElse(BalancePartition(liability.id, balance = 0.0, LocalDate.now())) // TODO remove after testing
            PieChartData.Slice(value = partition.balance.toFloat(), color = accountBackgroundColor(liability.color))
        }
        if (pieSlices.isEmpty()) {
            pieSlices = listOf(PieChartData.Slice(value = 1f, color = CapitalColors.GreyMedium))
        }
        return AnalyticsDataSet(
            accounts = liabilities,
            chart = Chart.PieChart(
                data = PieChartData(pieSlices)
            )
        )
    }

    private fun calculateValuesForAccountIds(partitions: BalancePartitions, ids: List<Long>): List<Float> {
        val partitionsByIds = partitions.entries.filterKeys { ids.contains(it) }
        return if (partitionsByIds.isEmpty()) {
            partitions.periods.map { 0f }
        } else {
            val accumulatedValues = mutableMapOf<LocalDate, Float>()
            partitionsByIds.forEach { (_, partitions) ->
                partitions.forEach {
                    accumulatedValues[it.period] = it.balance.toFloat() + accumulatedValues[it.period].orElse(0f)
                }
            }
            accumulatedValues.map { it.value }
        }
    }
}
