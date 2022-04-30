package com.harper.capital.repository.currency.mapper

import com.harper.capital.database.entity.CurrencyRateEntity
import com.harper.capital.network.response.LatestRatesResponse

internal object LatestRatesResponseEntityMapper : (LatestRatesResponse) -> List<CurrencyRateEntity> {

    override fun invoke(response: LatestRatesResponse): List<CurrencyRateEntity> {
        return if (response.success) {
            response.rates.map { (key, rate) ->
                CurrencyRateEntity(
                    id = key.hashCode().toLong(),
                    name = key,
                    rate = rate
                )
            }
        } else {
            emptyList()
        }
    }
}
