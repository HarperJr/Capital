package com.harper.capital.asset.domain

import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.AccountColor
import com.harper.capital.domain.model.AccountIcon
import com.harper.capital.domain.model.AccountMetadata
import com.harper.capital.domain.model.AccountMetadataType
import com.harper.capital.domain.model.AccountType
import com.harper.capital.domain.model.Currency
import com.harper.capital.repository.AccountRepository
import kotlinx.coroutines.coroutineScope

class UpdateAssetUseCase(private val accountRepository: AccountRepository) {

    suspend operator fun invoke(
        id: Long,
        name: String,
        amount: Double,
        currency: Currency,
        color: AccountColor,
        icon: AccountIcon,
        metadataType: AccountMetadataType?,
        isIncluded: Boolean,
        isActive: Boolean
    ) = coroutineScope {
        accountRepository.update(
            Account(
                id = id,
                name = name,
                balance = amount,
                type = AccountType.ASSET,
                currency = currency,
                color = color,
                icon = icon,
                isIncluded = isIncluded,
                isArchived = isActive,
                metadata = when (metadataType) {
                    AccountMetadataType.LOAN -> AccountMetadata.LoanAsset(0.0)
                    AccountMetadataType.GOAL -> AccountMetadata.GoalAsset(0.0)
                    else -> null
                }
            )
        )
    }
}
