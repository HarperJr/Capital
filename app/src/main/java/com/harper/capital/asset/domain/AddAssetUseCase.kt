package com.harper.capital.asset.domain

import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.AccountColor
import com.harper.capital.domain.model.AccountIcon
import com.harper.capital.domain.model.AccountMetadata
import com.harper.capital.domain.model.AccountMetadataType
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
        metadataType: AccountMetadataType
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
                metadata = createMetadataByType(metadataType)
            )
        )
    }

    private fun createMetadataByType(metadataType: AccountMetadataType): AccountMetadata? = when (metadataType) {
        AccountMetadataType.LOAN -> AccountMetadata.LoanAsset(limit = 0.0)
        AccountMetadataType.GOAL -> AccountMetadata.GoalAsset(goal = 0.0)
        AccountMetadataType.INVESTMENT -> AccountMetadata.Investment(percent = 0.1)
        else -> null
    }
}
