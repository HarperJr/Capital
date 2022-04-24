package com.harper.capital.prefs

import android.content.Context
import com.harper.capital.SettingsProto
import com.harper.capital.domain.model.ColorTheme
import com.harper.capital.domain.model.Currency
import com.harper.capital.domain.model.Settings
import com.harper.core.ext.orElse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId


class SettingsProvider(private val context: Context) {
    val asFlow: Flow<Settings> = context.settingsDataStore.data.map { mapToSettings(it) }

    suspend fun provide(): Settings = mapToSettings(context.settingsDataStore.data.first())

    suspend fun updateColorTheme(colorTheme: ColorTheme) =
        update { it.setColorTheme(colorTheme.name) }

    suspend fun updateCurrency(currency: Currency) =
        update { it.setCurrency(currency.name) }

    suspend fun updateCurrencyLastUpdate(timeInMillis: Long) {
        update { it.setCurrencyLastUpdate(timeInMillis) }
    }

    private suspend fun update(update: (SettingsProto.Builder) -> SettingsProto.Builder) {
        context.settingsDataStore.updateData { current ->
            update.invoke(current.toBuilder())
                .build()
        }
    }

    private fun mapToSettings(proto: SettingsProto): Settings = with(proto) {
        Settings(
            colorTheme = colorTheme.takeIf { it.isNotEmpty() }?.let(ColorTheme::valueOf).orElse(ColorTheme.SYSTEM),
            currency = currency.takeIf { it.isNotEmpty() }?.let(Currency::valueOf).orElse(Currency.RUB),
            currencyLastUpdate = LocalDateTime.ofInstant(Instant.ofEpochMilli(currencyLastUpdate), ZoneId.systemDefault())
        )
    }
}