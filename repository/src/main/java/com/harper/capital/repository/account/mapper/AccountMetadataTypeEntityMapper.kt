package com.harper.capital.repository.account.mapper

import com.harper.capital.database.entity.AccountEntityMetadataType
import com.harper.capital.domain.model.AccountMetadata

internal object AccountMetadataTypeEntityMapper : (AccountMetadata?) -> AccountEntityMetadataType? {

    override fun invoke(model: AccountMetadata?): AccountEntityMetadataType = when (model) {
        is AccountMetadata.LoanAsset -> AccountEntityMetadataType.LOAN
        is AccountMetadata.GoalAsset -> AccountEntityMetadataType.GOAL
        is AccountMetadata.Investment -> AccountEntityMetadataType.INVESTMENT
        else -> AccountEntityMetadataType.UNDEFINED
    }
}
