package com.harper.core.ext

import android.icu.text.NumberFormat

fun Double.format(currencyIso: String): String = NumberFormat.getCurrencyInstance()
    .apply { this.currency = android.icu.util.Currency.getInstance(currencyIso.uppercase()) }
    .format(this)
