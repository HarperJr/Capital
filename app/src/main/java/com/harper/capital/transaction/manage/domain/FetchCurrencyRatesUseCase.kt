package com.harper.capital.transaction.manage.domain

import com.harper.capital.domain.model.CurrencyRate
import com.harper.capital.repository.currency.CurrencyRepository
import kotlinx.coroutines.flow.first

class FetchCurrencyRatesUseCase(private val currencyRepository: CurrencyRepository) {

    suspend operator fun invoke(): List<CurrencyRate> = currencyRepository.fetchAll().first()
}
