package com.harper.capital.repository.transaction.mapper

import com.harper.capital.database.entity.embedded.TransactionEntityEmbedded
import com.harper.capital.domain.model.ChangeTransaction
import com.harper.capital.domain.model.Transaction
import com.harper.capital.domain.model.TransferTransaction
import com.harper.capital.repository.account.mapper.AccountMapper

private const val CHANGE_LEDGERS_SIZE = 1
private const val TRANSFER_LEDGERS_SIZE = 2

internal object TransactionMapper : (TransactionEntityEmbedded) -> Transaction {

    override fun invoke(entity: TransactionEntityEmbedded): Transaction =
        with(entity) {
            when (ledgers.size) {
                CHANGE_LEDGERS_SIZE -> {
                    ChangeTransaction(
                        id = transaction.id,
                        account = AccountMapper(ledgers[0].account, null, null),
                        amount = ledgers[0].ledger.amount,
                        dateTime = transaction.dateTime,
                        comment = transaction.comment,
                        isScheduled = transaction.isScheduled
                    )
                }
                TRANSFER_LEDGERS_SIZE -> {
                    TransferTransaction(
                        id = transaction.id,
                        source = AccountMapper(ledgers[0].account, null, null),
                        receiver = AccountMapper(ledgers[1].account, null, null),
                        sourceAmount = ledgers[0].ledger.amount,
                        receiverAmount = ledgers[1].ledger.amount,
                        dateTime = transaction.dateTime,
                        comment = transaction.comment,
                        isScheduled = transaction.isScheduled
                    )
                }
                else -> {
                    Transaction(
                        id = transaction.id,
                        ledgers = ledgers.map(LedgerMapper),
                        dateTime = transaction.dateTime,
                        comment = transaction.comment,
                        isScheduled = transaction.isScheduled
                    )
                }
            }
        }
}
