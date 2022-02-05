package com.harper.capital.database.entity.embedded

import androidx.room.Embedded
import androidx.room.Relation
import com.harper.capital.database.entity.AccountEntity
import com.harper.capital.database.entity.AccountTable
import com.harper.capital.database.entity.LedgerEntity
import com.harper.capital.database.entity.LedgerTable

data class LedgerEntityEmbedded(
    @Embedded
    val ledger: LedgerEntity,
    @Relation(
        parentColumn = LedgerTable.accountId,
        entityColumn = AccountTable.id
    )
    val account: AccountEntity
)
