package com.harper.capital.repository.transaction.mapper

import com.harper.capital.database.entity.BalancePartitionEntity
import com.harper.capital.domain.model.BalancePartition
import com.harper.core.ext.orElse

internal object BalancePartitionMapper : (BalancePartitionEntity) -> BalancePartition {

    override fun invoke(entity: BalancePartitionEntity): BalancePartition = with(entity) {
        BalancePartition(
            accountId = accountId,
            balance = balance.orElse(0.0),
            period = period.withDayOfMonth(1).toLocalDate()
        )
    }
}
