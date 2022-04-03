package com.harper.capital.analytics.domain.model

import com.harper.capital.domain.model.BalancePartition
import java.time.LocalDate

class BalancePartitions(
    val balanceEntries: Map<Long, List<BalancePartition>>,
    val periods: List<LocalDate>
)
