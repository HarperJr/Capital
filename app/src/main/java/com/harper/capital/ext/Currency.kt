package com.harper.capital.ext

import com.harper.capital.domain.model.Currency
import com.harper.capital.domain.model.CurrencyRate

fun Currency.getExchangeRate(
    rates: List<CurrencyRate>,
    targetCurrency: Currency
): Double {
    val sourceRate = rates.first { it.currency == this }
    val targetRate = rates.first { it.currency == targetCurrency }
    return sourceRate.rate / targetRate.rate
}
