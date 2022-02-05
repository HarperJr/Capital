package com.harper.capital.repository.mapper

import com.harper.capital.database.entity.LedgerEntityType
import com.harper.capital.database.entity.embedded.LedgerEntityEmbedded
import com.harper.capital.domain.model.Ledger
import com.harper.capital.domain.model.LedgerType

internal object LedgerMapper : (LedgerEntityEmbedded) -> Ledger {

    override fun invoke(entity: LedgerEntityEmbedded): Ledger = with(entity) {
        Ledger(
            account = AccountMapper(entity.account, null, null),
            type = when (ledger.type) {
                LedgerEntityType.DEBIT -> LedgerType.DEBIT
                LedgerEntityType.CREDIT -> LedgerType.CREDIT
            }
        )
    }
}
