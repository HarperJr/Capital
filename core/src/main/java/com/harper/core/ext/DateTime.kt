package com.harper.core.ext

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

enum class TimePattern(val formatValue: String) {
    LLLL("LLLL");

    fun getFormatter(): DateTimeFormatter = DateTimeFormatter.ofPattern(this.formatValue, Locale.getDefault())
}

fun LocalDateTime.formatBy(pattern: TimePattern) = pattern.getFormatter().format(this)

fun LocalDate.formatBy(pattern: TimePattern) = pattern.getFormatter().format(this)

fun CharSequence.tryParse(pattern: TimePattern): LocalDateTime =
    runCatching { LocalDateTime.parse(this, pattern.getFormatter()) }
        .onFailure { LocalDate.parse(this, pattern.getFormatter()).atStartOfDay(TimeZone.getDefault().toZoneId()) }
        .getOrThrow()
