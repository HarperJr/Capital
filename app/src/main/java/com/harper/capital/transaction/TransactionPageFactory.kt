package com.harper.capital.transaction

import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.AccountMetadata
import com.harper.capital.domain.model.AccountType
import com.harper.capital.transaction.model.AccountDataSet
import com.harper.capital.transaction.model.TransactionPage
import com.harper.capital.transaction.model.TransactionType

class TransactionPageFactory {

    fun create(type: TransactionType, accounts: List<Account>): TransactionPage = when (type) {
        TransactionType.LIABILITY -> createLiabilityPage(accounts)
        TransactionType.INCOME -> createIncomePage(accounts)
        TransactionType.SEND -> createSendPage(accounts)
        TransactionType.DEBT -> createDebtPage(accounts)
    }

    private fun createLiabilityPage(accounts: List<Account>): TransactionPage = TransactionPage(
        type = TransactionType.LIABILITY,
        sourceDataSet = AccountDataSet(
            type = AccountType.ASSET,
            accounts = accounts.filter { it.type == AccountType.ASSET }
        ),
        receiverDataSet = AccountDataSet(
            type = AccountType.LIABILITY,
            accounts = accounts.filter { it.type == AccountType.LIABILITY && it.metadata !is AccountMetadata.Debt }
        )
    )

    private fun createIncomePage(accounts: List<Account>): TransactionPage = TransactionPage(
        type = TransactionType.INCOME,
        sourceDataSet = AccountDataSet(
            type = AccountType.LIABILITY,
            accounts = accounts.filter { it.type == AccountType.INCOME }
        ),
        receiverDataSet = AccountDataSet(
            type = AccountType.ASSET,
            accounts = accounts.filter { it.type == AccountType.ASSET }
        )
    )

    private fun createSendPage(accounts: List<Account>): TransactionPage = TransactionPage(
        type = TransactionType.SEND,
        sourceDataSet = AccountDataSet(
            type = AccountType.ASSET,
            accounts = accounts.filter { it.type == AccountType.ASSET }
        ),
        receiverDataSet = AccountDataSet(
            type = AccountType.ASSET,
            accounts = accounts.filter { it.type == AccountType.ASSET }
        ),
    )

    private fun createDebtPage(accounts: List<Account>): TransactionPage = TransactionPage(
        type = TransactionType.DEBT,
        sourceDataSet = AccountDataSet(
            type = AccountType.ASSET,
            accounts = accounts.filter { it.type == AccountType.ASSET }
        ),
        receiverDataSet = AccountDataSet(
            type = AccountType.LIABILITY,
            accounts = accounts.filter { it.type == AccountType.LIABILITY && it.metadata is AccountMetadata.Debt }
        )
    )
}
