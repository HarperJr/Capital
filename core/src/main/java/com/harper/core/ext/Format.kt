package com.harper.core.ext

import android.icu.text.DecimalFormat
import android.icu.text.NumberFormat
import android.icu.util.Currency
import android.icu.util.ULocale
import java.util.Locale

fun Double.formatWithCurrencySymbol(
    currencyIso: String,
    minFractionDigits: Int = 2,
    maxFractionDigits: Int = 2
): String = NumberFormat.getCurrencyInstance(ULocale("ru"))
    .apply {
        this.currency = Currency.getInstance(currencyIso.uppercase())
        this.minimumFractionDigits = minFractionDigits
        this.maximumFractionDigits = maxFractionDigits
    }
    .format(this)

fun Double.formatAmount(
    currencyIso: String? = null,
    minFractionDigits: Int = 2,
    maxFractionDigits: Int = 2
): String = if (currencyIso == null) {
    NumberFormat.getInstance(ULocale("ru"))
        .apply {
            this.minimumFractionDigits = minFractionDigits
            this.maximumFractionDigits = maxFractionDigits
        }
        .format(this)
} else {
    this.formatWithCurrencySymbol(currencyIso, minFractionDigits, maxFractionDigits)
}

fun String.formatCurrencySymbol(): String = Currency.getInstance(this.uppercase())
    .getSymbol(Locale("ru"))

fun String.formatCurrencyName(): String = Currency.getInstance(this.uppercase())
    .getDisplayName(Locale.getDefault())

fun String.parseAmountWithCurrency(currencyIso: String): Double =
    NumberFormat.getCurrencyInstance(ULocale("ru"))
        .apply { this.currency = Currency.getInstance(currencyIso.uppercase()) }
        .parse(this)
        .toDouble()

fun String.parseAmount(currencyIso: String? = null): Double {
    return if (currencyIso == null) {
        NumberFormat.getInstance(ULocale("ru"))
            .parse(this)
            .toDouble()
    } else {
        this.parseAmountWithCurrency(currencyIso)
    }
}
