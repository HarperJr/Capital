package com.harper.capital.repository.transaction.mapper

import com.harper.capital.database.entity.BalancePartitionEntityPeriod
import com.harper.capital.domain.model.BalancePartitionPeriod

internal object BalancePartitionPeriodEntityMapper : (BalancePartitionPeriod) -> BalancePartitionEntityPeriod {

    override fun invoke(model: BalancePartitionPeriod): BalancePartitionEntityPeriod = when (model) {
        BalancePartitionPeriod.DAY -> BalancePartitionEntityPeriod.DAY
        BalancePartitionPeriod.MONTH -> BalancePartitionEntityPeriod.MONTH
        BalancePartitionPeriod.QUARTER -> BalancePartitionEntityPeriod.QUARTER
        BalancePartitionPeriod.YEAR -> BalancePartitionEntityPeriod.YEAR
    }
}
