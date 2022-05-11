package com.harper.capital.database.entity

enum class BalancePartitionEntityPeriod(val periodInMillis: Long) {
    DAY(86400000L),
    WEEK(604800000L),
    MONTH(2629800000L),
    QUARTER(7889400000L),
    YEAR(31557600000L)
}
