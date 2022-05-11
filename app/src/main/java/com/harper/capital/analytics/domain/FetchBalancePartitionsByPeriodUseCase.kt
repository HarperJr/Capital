package com.harper.capital.analytics.domain

import com.harper.capital.analytics.domain.model.BalancePartitions
import com.harper.capital.domain.model.BalancePartition
import com.harper.capital.domain.model.BalancePartitionPeriod
import com.harper.capital.repository.transaction.TransactionRepository
import com.harper.core.ext.orElse
import java.time.LocalDate
import java.time.Period
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.time.Duration.Companion.days

class FetchBalancePartitionsByPeriodUseCase(private val transactionRepository: TransactionRepository) {
    private val partitionAlignFactory: PartitionAlignFactory = PartitionAlignFactory()

    operator fun invoke(period: BalancePartitionPeriod, dateStart: LocalDate, dateEnd: LocalDate): Flow<BalancePartitions> =
        transactionRepository.fetchBalancePartitionsByPeriod(period)
            .map { partitions ->
                if (partitions.isEmpty()) {
                    BalancePartitions(entries = emptyMap(), periods = emptyList())
                } else {
                    val aligner = partitionAlignFactory.create(period)
                    val periods = aligner.calculatePeriods(
                        minDate = partitions.minOf { it.period }.takeIf { it.isBefore(dateStart) }.orElse(dateStart),
                        maxDate = partitions.maxOf { it.period }.takeIf { it.isAfter(dateEnd) }.orElse(dateEnd)
                    )
                    val entries = partitions.groupBy { it.accountId }
                        .mapValues { (accountId, partitions) ->
                            var prevPartition: BalancePartition? = null
                            periods.map { period ->
                                partitions.find { aligner.isPartitionInPeriod(it, period) }
                                    ?.also { prevPartition = it }
                                    .orElse(
                                        prevPartition?.copy(period = period)
                                            .orElse(BalancePartition(accountId, balance = 0.0, period = period))
                                    )
                            }
                        }
                    BalancePartitions(entries = entries, periods = periods)
                }
            }
}

class PartitionAlignFactory {

    fun create(period: BalancePartitionPeriod): PartitionAligner = when (period) {
        BalancePartitionPeriod.DAY -> DayPartitionAligner()
        BalancePartitionPeriod.WEEK -> WeekPartitionAligner()
        BalancePartitionPeriod.MONTH -> MonthPartitionAligner()
        BalancePartitionPeriod.QUARTER -> QuarterPartitionAligner()
        BalancePartitionPeriod.YEAR -> YearPartitionAligner()
    }
}

interface PartitionAligner {

    fun calculatePeriods(minDate: LocalDate, maxDate: LocalDate): List<LocalDate>

    fun isPartitionInPeriod(partition: BalancePartition, period: LocalDate): Boolean
}

class DayPartitionAligner : PartitionAligner {

    override fun calculatePeriods(minDate: LocalDate, maxDate: LocalDate): List<LocalDate> {
        val diff = Period.between(minDate, maxDate).let {
            (it.years * 12 + it.months).days.inWholeDays
        }
        return (0..diff).map { minDate.plusDays(it) }
    }

    override fun isPartitionInPeriod(partition: BalancePartition, period: LocalDate): Boolean =
        Period.between(partition.period, period).let {
            (it.years * 12 + it.months).days.inWholeDays
        } == 0L
}

class WeekPartitionAligner : PartitionAligner {

    override fun calculatePeriods(minDate: LocalDate, maxDate: LocalDate): List<LocalDate> {
        val diff = Period.between(minDate, maxDate).let {
            (it.years * 12 + it.months).days.inWholeDays / 7
        }
        return (0..diff).map { minDate.plusDays(it * 7) }
    }

    override fun isPartitionInPeriod(partition: BalancePartition, period: LocalDate): Boolean =
        Period.between(partition.period, period).let {
            (it.years * 12 + it.months).days.inWholeDays / 7
        } == 0L
}

class MonthPartitionAligner : PartitionAligner {

    override fun calculatePeriods(minDate: LocalDate, maxDate: LocalDate): List<LocalDate> {
        val diff = Period.between(minDate, maxDate).toTotalMonths()
        return (0..diff).map { minDate.plusMonths(it) }
    }

    override fun isPartitionInPeriod(partition: BalancePartition, period: LocalDate): Boolean =
        Period.between(partition.period, period).toTotalMonths() == 0L
}


class QuarterPartitionAligner : PartitionAligner {

    override fun calculatePeriods(minDate: LocalDate, maxDate: LocalDate): List<LocalDate> {
        val diff = Period.between(minDate, maxDate).toTotalMonths() / 3
        return (0..diff).map { minDate.plusMonths(it * 3) }
    }

    override fun isPartitionInPeriod(partition: BalancePartition, period: LocalDate): Boolean =
        Period.between(partition.period, period).toTotalMonths() / 3L == 0L
}

class YearPartitionAligner : PartitionAligner {

    override fun calculatePeriods(minDate: LocalDate, maxDate: LocalDate): List<LocalDate> {
        val diff = Period.between(minDate, maxDate).years
        return (0..diff).map { minDate.plusYears(it.toLong()) }
    }

    override fun isPartitionInPeriod(partition: BalancePartition, period: LocalDate): Boolean =
        Period.between(partition.period, period).years == 0
}
