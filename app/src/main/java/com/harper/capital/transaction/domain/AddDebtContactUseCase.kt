package com.harper.capital.transaction.domain

import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.AccountColor
import com.harper.capital.domain.model.AccountIcon
import com.harper.capital.domain.model.AccountMetadata
import com.harper.capital.domain.model.AccountType
import com.harper.capital.domain.model.Contact
import com.harper.capital.domain.model.Currency
import com.harper.capital.prefs.SettingsProvider
import com.harper.capital.repository.account.AccountRepository

class AddDebtContactUseCase(private val accountRepository: AccountRepository, private val settingsProvider: SettingsProvider) {

    suspend operator fun invoke(contact: Contact) = with(contact) {
        val settings = settingsProvider.provide()
        accountRepository.insert(
            Account(
                0L,
                name,
                type = AccountType.LIABILITY,
                color = AccountColor.DEBT,
                icon = AccountIcon.DEBT,
                currency = settings.currency,
                balance = 0.0,
                metadata = AccountMetadata.Debt(avatar, phone)
            )
        )
    }
}
