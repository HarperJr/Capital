package com.harper.capital.repository.currency.mapper

import com.harper.capital.database.entity.CurrencyRateEntity
import com.harper.capital.domain.model.Currency
import com.harper.capital.domain.model.CurrencyRate

internal object CurrencyRateMapper : (List<CurrencyRateEntity>) -> List<CurrencyRate> {

    override fun invoke(entities: List<CurrencyRateEntity>): List<CurrencyRate> = entities.map {
        CurrencyRate(
            currency = Currency.valueOf(it.name),
            isBase = it.isBase,
            rate = it.rate
        )
    }
}
