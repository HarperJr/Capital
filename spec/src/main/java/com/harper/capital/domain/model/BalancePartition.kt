package com.harper.capital.domain.model

import java.time.LocalDate

data class BalancePartition(
    val accountId: Long,
    val balance: Double,
    val period: LocalDate
)
