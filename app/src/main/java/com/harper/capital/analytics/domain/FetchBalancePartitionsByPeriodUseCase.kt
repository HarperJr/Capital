package com.harper.capital.analytics.domain

import com.harper.capital.analytics.domain.model.BalancePartitions
import com.harper.capital.domain.model.BalancePartition
import com.harper.capital.domain.model.BalancePartitionPeriod
import com.harper.capital.repository.transaction.TransactionRepository
import com.harper.core.ext.orElse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.Period

class FetchBalancePartitionsByPeriodUseCase(private val transactionRepository: TransactionRepository) {
    private val partitionAlignFactory: PartitionAlignFactory = PartitionAlignFactory()

    operator fun invoke(period: BalancePartitionPeriod): Flow<BalancePartitions> =
        transactionRepository.fetchBalancePartitionsByPeriod(period)
            .map { partitions ->
                if (partitions.isEmpty()) {
                    BalancePartitions(balanceEntries = emptyMap(), periods = emptyList())
                } else {
                    val aligner = partitionAlignFactory.create(period)
                    val periods = aligner.calculatePeriods(partitions.first(), partitions.last())
                    val entries = partitions.groupBy { it.accountId }
                        .mapValues { (_, partitions) ->
                            var prevPartition = partitions.first()
                            periods.map { period ->
                                partitions.find { aligner.isPartitionInPeriod(it, period) }
                                    ?.also { prevPartition = it }
                                    .orElse(prevPartition.copy(period = period))
                            }
                        }
                    BalancePartitions(balanceEntries = entries, periods = periods)
                }
            }
}

class PartitionAlignFactory {

    fun create(period: BalancePartitionPeriod): PartitionAligner = when (period) {
        BalancePartitionPeriod.DAY -> DayPartitionAligner()
        BalancePartitionPeriod.MONTH -> MonthPartitionAligner()
        BalancePartitionPeriod.QUARTER -> QuarterPartitionAligner()
        BalancePartitionPeriod.YEAR -> YearPartitionAligner()
    }
}

interface PartitionAligner {

    fun calculatePeriods(first: BalancePartition, last: BalancePartition): List<LocalDate>

    fun isPartitionInPeriod(partition: BalancePartition, period: LocalDate): Boolean
}

class DayPartitionAligner : PartitionAligner {

    override fun calculatePeriods(first: BalancePartition, last: BalancePartition): List<LocalDate> {
        val diff = Period.between(first.period, last.period).days
        return (0..diff).map { first.period.plusDays(it.toLong()) }
    }

    override fun isPartitionInPeriod(partition: BalancePartition, period: LocalDate): Boolean =
        Period.between(partition.period, period).days == 0
}

class MonthPartitionAligner : PartitionAligner {

    override fun calculatePeriods(first: BalancePartition, last: BalancePartition): List<LocalDate> {
        val diff = Period.between(first.period, last.period).months
        return (0..diff).map { first.period.plusMonths(it.toLong()) }
    }

    override fun isPartitionInPeriod(partition: BalancePartition, period: LocalDate): Boolean =
        Period.between(partition.period, period).months == 0
}


class QuarterPartitionAligner : PartitionAligner {

    override fun calculatePeriods(first: BalancePartition, last: BalancePartition): List<LocalDate> {
        val diff = Period.between(first.period, last.period).months * 3
        return (0..diff).map { first.period.plusMonths(it.toLong() * 3) }
    }

    override fun isPartitionInPeriod(partition: BalancePartition, period: LocalDate): Boolean =
        Period.between(partition.period, period).months / 3 == 0
}

class YearPartitionAligner : PartitionAligner {

    override fun calculatePeriods(first: BalancePartition, last: BalancePartition): List<LocalDate> {
        val diff = Period.between(first.period, last.period).years
        return (0..diff).map { first.period.plusYears(it.toLong()) }
    }

    override fun isPartitionInPeriod(partition: BalancePartition, period: LocalDate): Boolean =
        Period.between(partition.period, period).years == 0
}
