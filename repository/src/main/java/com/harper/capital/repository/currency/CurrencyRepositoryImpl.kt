package com.harper.capital.repository.currency

import com.harper.capital.database.dao.CurrencyDao
import com.harper.capital.domain.model.Currency
import com.harper.capital.domain.model.CurrencyRate
import com.harper.capital.network.api.ExchangeApi
import com.harper.capital.repository.currency.mapper.CurrencyRateMapper
import com.harper.capital.repository.currency.mapper.LatestRatesResponseEntityMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CurrencyRepositoryImpl(
    private val exchangeApi: ExchangeApi,
    private val currencyDao: CurrencyDao
) : CurrencyRepository {

    override suspend fun update(baseCurrency: Currency): Long {
        val currencySymbols = Currency.values().joinToString(separator = ",") { it.name }
        val exchange = exchangeApi.getLatest(symbols = currencySymbols)
        currencyDao.insert(LatestRatesResponseEntityMapper(exchange))
        return exchange.timestamp
    }

    override fun fetchAll(): Flow<List<CurrencyRate>> = currencyDao.selectAll()
        .map { CurrencyRateMapper(it) }
}
