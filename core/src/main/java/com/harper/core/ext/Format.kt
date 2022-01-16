package com.harper.core.ext

import android.icu.text.DecimalFormat
import android.icu.text.DecimalFormatSymbols
import android.icu.text.NumberFormat
import android.icu.util.Currency
import android.icu.util.ULocale
import java.util.*

fun Double.formatWithCurrencySymbol(currencyIso: String): String = NumberFormat.getCurrencyInstance(ULocale("ru"))
    .apply { this.currency = Currency.getInstance(currencyIso.uppercase()) }
    .format(this)

fun String.parseAmountWithCurrency(currencyIso: String): Double = NumberFormat.getCurrencyInstance(ULocale("ru"))
    .apply { this.currency = Currency.getInstance(currencyIso.uppercase()) }
    .parse(this)
    .toDouble()

fun Double.formatWithoutZeroDecimal(): String = DecimalFormat("#.##", DecimalFormatSymbols.getInstance(ULocale.US))
    .format(this)
    .let { formattedValue ->
        val decimalSeparatorIndex = formattedValue.indexOf('.')
        if (decimalSeparatorIndex != -1 && formattedValue.substring(decimalSeparatorIndex).all { it == '0' }) {
            formattedValue.substring(startIndex = 0, endIndex = decimalSeparatorIndex)
        } else {
            formattedValue
        }
    }

fun Double.formatAmount(): String = NumberFormat.getInstance(ULocale("ru"))
    .format(this)

fun String.parseAmount(): Double = NumberFormat.getInstance(ULocale("ru"))
    .parse(this)
    .toDouble()

fun String.formatCurrencySymbol(): String = Currency.getInstance(this.uppercase())
    .getSymbol(Locale("ru"))

fun String.formatCurrencyName(): String = Currency.getInstance(this.uppercase())
    .getDisplayName(Locale.getDefault())

