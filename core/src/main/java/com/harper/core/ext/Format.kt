package com.harper.core.ext

import android.icu.text.NumberFormat
import android.icu.util.ULocale

fun Double.format(currencyIso: String): String = NumberFormat.getCurrencyInstance(ULocale("ru"))
    .apply { this.currency = android.icu.util.Currency.getInstance(currencyIso.uppercase()) }
    .format(this)
