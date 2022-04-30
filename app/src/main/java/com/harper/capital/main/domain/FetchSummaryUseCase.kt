package com.harper.capital.main.domain

import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.Currency
import com.harper.capital.domain.model.CurrencyRate
import com.harper.capital.ext.getExchangeRate
import com.harper.capital.main.domain.model.Summary
import com.harper.capital.prefs.SettingsProvider
import com.harper.capital.repository.currency.CurrencyRepository
import com.harper.capital.repository.transaction.TransactionRepository
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class FetchSummaryUseCase(
    private val transactionRepository: TransactionRepository,
    private val currencyRepository: CurrencyRepository,
    private val settingsProvider: SettingsProvider
) {

    operator fun invoke(): Flow<Summary> {
        val currentDate = LocalDate.now()
        val startOfMonth = currentDate.withDayOfMonth(1).atStartOfDay()
        val endOfMonth = startOfMonth.plusMonths(1L)
        return combine(
            transactionRepository.fetchBalance(),
            transactionRepository.fetchLiabilitiesBetween(dateTimeAfter = startOfMonth, dateTimeBefore = endOfMonth),
            settingsProvider.asFlow,
            currencyRepository.fetchAll()
        ) { balance, expenses, settings, rates ->
            Summary(
                expenses = calculateExchangedSum(expenses, settings.currency, rates),
                balance = calculateExchangedSum(balance, settings.currency, rates),
                currency = settings.currency
            )
        }
    }

    private fun calculateExchangedSum(accounts: List<Account>, currency: Currency, rates: List<CurrencyRate>): Double =
        accounts.sumOf {
            it.balance * currency.getExchangeRate(rates, it.currency)
        }
}
