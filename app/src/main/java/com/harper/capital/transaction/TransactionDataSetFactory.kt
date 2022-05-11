package com.harper.capital.transaction

import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.AccountMetadata
import com.harper.capital.domain.model.AccountType
import com.harper.capital.transaction.model.AccountDataSet
import com.harper.capital.transaction.model.DataSetSection
import com.harper.capital.transaction.model.SelectableAccount
import com.harper.capital.transaction.model.TransactionType

class TransactionDataSetFactory {

    fun create(
        type: TransactionType,
        selectedAccountId: Long?,
        accounts: List<Account>
    ): Map<DataSetSection, AccountDataSet> = when (type) {
        TransactionType.EXPENSE -> createExpenseDataSets(selectedAccountId, accounts)
        TransactionType.INCOME -> createIncomeDataSets(selectedAccountId, accounts)
        TransactionType.SEND -> createSendDataSets(selectedAccountId, accounts)
        TransactionType.DEBT -> createDutyDataSets(selectedAccountId, accounts)
    }

    private fun createExpenseDataSets(
        selectedAccountId: Long?,
        accounts: List<Account>
    ): Map<DataSetSection, AccountDataSet> = mapOf(
        DataSetSection.FROM to
                AccountDataSet(
                    type = AccountType.ASSET,
                    accounts = accounts
                        .filter { it.type == AccountType.ASSET }
                        .map { SelectableAccount(account = it) },
                    selectedAccountId = selectedAccountId
                ),
        DataSetSection.TO to
                AccountDataSet(
                    type = AccountType.LIABILITY,
                    accounts = accounts
                        .filter { it.type == AccountType.LIABILITY && it.metadata !is AccountMetadata.Debt }
                        .map { SelectableAccount(account = it, isEnabled = selectedAccountId != it.id) }
                )
    )

    private fun createIncomeDataSets(
        selectedAccountId: Long?,
        accounts: List<Account>
    ): Map<DataSetSection, AccountDataSet> = mapOf(
        DataSetSection.FROM to
                AccountDataSet(
                    type = AccountType.LIABILITY,
                    accounts = accounts
                        .filter { it.type == AccountType.INCOME }
                        .map { SelectableAccount(account = it, isEnabled = selectedAccountId != it.id) }
                ),
        DataSetSection.TO to
                AccountDataSet(
                    type = AccountType.ASSET,
                    accounts = accounts
                        .filter { it.type == AccountType.ASSET }
                        .map { SelectableAccount(account = it) },
                    selectedAccountId = selectedAccountId
                )
    )

    private fun createSendDataSets(
        selectedAccountId: Long?,
        accounts: List<Account>
    ): Map<DataSetSection, AccountDataSet> = mapOf(
        DataSetSection.FROM to
                AccountDataSet(
                    type = AccountType.ASSET,
                    accounts = accounts
                        .filter { it.type == AccountType.ASSET }
                        .map { SelectableAccount(account = it) },
                    selectedAccountId = selectedAccountId
                ),
        DataSetSection.TO to
                AccountDataSet(
                    type = AccountType.ASSET,
                    accounts = accounts
                        .filter { it.type == AccountType.ASSET }
                        .map { SelectableAccount(account = it, isEnabled = selectedAccountId != it.id) }
                ),
    )

    private fun createDutyDataSets(selectedAccountId: Long?, accounts: List<Account>): Map<DataSetSection, AccountDataSet> = mapOf(
        DataSetSection.FROM to
                AccountDataSet(
                    type = AccountType.ASSET,
                    accounts = accounts
                        .filter { it.type == AccountType.ASSET }
                        .map { SelectableAccount(account = it) },
                    selectedAccountId = selectedAccountId
                ),
        DataSetSection.TO to
                AccountDataSet(
                    type = AccountType.LIABILITY,
                    accounts = accounts
                        .filter { it.type == AccountType.LIABILITY && it.metadata is AccountMetadata.Debt }
                        .map { SelectableAccount(account = it) }
                )
    )
}
