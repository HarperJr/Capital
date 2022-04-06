package com.harper.capital.analytics.domain.model

import com.harper.capital.domain.model.BalancePartition
import java.time.LocalDate

class BalancePartitions(
    val entries: Map<Long, List<BalancePartition>>,
    val periods: List<LocalDate>
)
