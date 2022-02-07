package com.harper.capital.repository.transaction.mapper

import com.harper.capital.database.entity.LedgerEntityType
import com.harper.capital.domain.model.LedgerType

internal object LedgerTypeEntityMapper : (LedgerType) -> LedgerEntityType {

    override fun invoke(model: LedgerType): LedgerEntityType = when (model) {
        LedgerType.DEBIT -> LedgerEntityType.DEBIT
        LedgerType.CREDIT -> LedgerEntityType.CREDIT
    }
}
