package com.harper.capital.repository.mapper

import com.harper.capital.database.entity.AccountEntityType
import com.harper.capital.database.entity.LedgerEntityType
import com.harper.capital.database.entity.embedded.TransactionEntityEmbedded
import com.harper.capital.domain.model.ChargeTransaction
import com.harper.capital.domain.model.Transaction
import com.harper.capital.domain.model.TransferTransaction

internal object TransactionMapper : (List<TransactionEntityEmbedded>) -> List<Transaction> {

    override fun invoke(entities: List<TransactionEntityEmbedded>): List<Transaction> =
        entities.map {
            when (it.ledgers.size) {
                1 -> {
                    val receiver = it.ledgers.first()
                    ChargeTransaction(
                        id = it.transaction.id,
                        receiver = AccountMapper(receiver.account, null, null),
                        amount = if (receiver.ledger.type == LedgerEntityType.DEBIT) receiver.ledger.amount else -receiver.ledger.amount,
                        dateTime = it.transaction.dateTime,
                        comment = it.transaction.comment,
                        isScheduled = it.transaction.isScheduled
                    )
                }
                2 -> {
                    val source = it.ledgers.first { it.ledger.type == LedgerEntityType.CREDIT }
                    val receiver = it.ledgers.first { it.ledger.type == LedgerEntityType.DEBIT }
                    TransferTransaction(
                        id = it.transaction.id,
                        source = AccountMapper(source.account, null, null),
                        receiver = AccountMapper(receiver.account, null, null),
                        amount = if (source.account.type == AccountEntityType.ASSET) -source.ledger.amount else receiver.ledger.amount,
                        dateTime = it.transaction.dateTime,
                        comment = it.transaction.comment,
                        isScheduled = it.transaction.isScheduled
                    )
                }
                else -> {
                    Transaction(
                        id = it.transaction.id,
                        ledgers = it.ledgers.map(LedgerMapper),
                        amount = it.ledgers.sumOf { it.ledger.amount },
                        dateTime = it.transaction.dateTime,
                        comment = it.transaction.comment,
                        isScheduled = it.transaction.isScheduled
                    )
                }
            }
        }
}
