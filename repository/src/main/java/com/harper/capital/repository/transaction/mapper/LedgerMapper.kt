package com.harper.capital.repository.transaction.mapper

import com.harper.capital.database.entity.LedgerEntityType
import com.harper.capital.database.entity.embedded.LedgerEntityEmbedded
import com.harper.capital.domain.model.Ledger
import com.harper.capital.domain.model.LedgerType
import com.harper.capital.repository.account.mapper.AccountMapper

internal object LedgerMapper : (LedgerEntityEmbedded) -> Ledger {

    override fun invoke(entity: LedgerEntityEmbedded): Ledger = with(entity) {
        Ledger(
            id = entity.ledger.id,
            account = AccountMapper(entity.account, null, null),
            type = when (ledger.type) {
                LedgerEntityType.DEBIT -> LedgerType.DEBIT
                LedgerEntityType.CREDIT -> LedgerType.CREDIT
            }
        )
    }
}
