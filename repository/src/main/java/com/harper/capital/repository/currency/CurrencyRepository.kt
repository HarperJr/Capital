package com.harper.capital.repository.currency

import com.harper.capital.domain.model.Currency
import com.harper.capital.domain.model.CurrencyRate
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {

    suspend fun update(baseCurrency: Currency): Long

    fun fetchAll(): Flow<List<CurrencyRate>>
}
