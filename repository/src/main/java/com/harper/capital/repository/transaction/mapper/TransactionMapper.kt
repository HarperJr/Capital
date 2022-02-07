package com.harper.capital.repository.transaction.mapper

import com.harper.capital.database.entity.AccountEntityType
import com.harper.capital.database.entity.LedgerEntityType
import com.harper.capital.database.entity.embedded.TransactionEntityEmbedded
import com.harper.capital.domain.model.ChargeTransaction
import com.harper.capital.domain.model.Transaction
import com.harper.capital.domain.model.TransferTransaction
import com.harper.capital.repository.account.mapper.AccountMapper

internal object TransactionMapper : (TransactionEntityEmbedded) -> Transaction {

    override fun invoke(entity: TransactionEntityEmbedded): Transaction =
        with(entity) {
            when (ledgers.size) {
                1 -> {
                    val receiver = ledgers.first()
                    ChargeTransaction(
                        id = transaction.id,
                        receiver = AccountMapper(receiver.account, null, null),
                        amount = if (receiver.ledger.type == LedgerEntityType.DEBIT) receiver.ledger.amount else -receiver.ledger.amount,
                        dateTime = transaction.dateTime,
                        comment = transaction.comment,
                        isScheduled = transaction.isScheduled
                    )
                }
                2 -> {
                    val source = ledgers.first { it.ledger.type == LedgerEntityType.CREDIT }
                    val receiver = ledgers.first { it.ledger.type == LedgerEntityType.DEBIT }
                    TransferTransaction(
                        id = transaction.id,
                        source = AccountMapper(source.account, null, null),
                        receiver = AccountMapper(receiver.account, null, null),
                        amount = if (receiver.account.type == AccountEntityType.LIABILITY) -receiver.ledger.amount else receiver.ledger.amount,
                        dateTime = transaction.dateTime,
                        comment = transaction.comment,
                        isScheduled = transaction.isScheduled
                    )
                }
                else -> {
                    Transaction(
                        id = transaction.id,
                        ledgers = ledgers.map(LedgerMapper),
                        amount = ledgers.sumOf { it.ledger.amount },
                        dateTime = transaction.dateTime,
                        comment = transaction.comment,
                        isScheduled = transaction.isScheduled
                    )
                }
            }
        }
}
