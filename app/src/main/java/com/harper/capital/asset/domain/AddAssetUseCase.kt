package com.harper.capital.asset.domain

import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.AccountColor
import com.harper.capital.domain.model.AccountIcon
import com.harper.capital.domain.model.AccountMetadata
import com.harper.capital.domain.model.AccountType
import com.harper.capital.domain.model.Currency
import com.harper.capital.repository.account.AccountRepository
import kotlinx.coroutines.coroutineScope

class AddAssetUseCase(private val accountRepository: AccountRepository) {

    suspend operator fun invoke(
        name: String,
        color: AccountColor,
        icon: AccountIcon,
        currency: Currency,
        balance: Double,
        isIncluded: Boolean,
        metadata: AccountMetadata?
    ) = coroutineScope {
        accountRepository.insert(
            Account(
                id = 0L,
                name = name,
                type = AccountType.ASSET,
                color = color,
                icon = icon,
                currency = currency,
                balance = balance,
                isIncluded = isIncluded,
                metadata = metadata
            )
        )
    }
}
