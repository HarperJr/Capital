package com.harper.capital.main.domain

import com.harper.capital.prefs.SettingsManager
import com.harper.capital.repository.currency.CurrencyRepository
import java.time.Duration
import java.time.LocalDateTime

const val CURRENCY_UPDATE_INTERVAL = 24

class UpdateCurrenciesUseCase(
    private val currencyRepository: CurrencyRepository,
    private val settingsManager: SettingsManager
) {

    suspend operator fun invoke() {
        val settings = settingsManager.provide()
        val needsUpdate = Duration.between(settings.currencyLastUpdate, LocalDateTime.now()).toHours() >= CURRENCY_UPDATE_INTERVAL
        if (needsUpdate) {
            val updateTime = currencyRepository.update(settings.currency)
            settingsManager.updateCurrencyLastUpdate(updateTime)
        }
    }
}
