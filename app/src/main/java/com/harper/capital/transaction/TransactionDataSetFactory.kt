package com.harper.capital.transaction

import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.AccountType
import com.harper.capital.transaction.model.AccountDataSet
import com.harper.capital.transaction.model.DataSetSection
import com.harper.capital.transaction.model.DataSetType
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
        TransactionType.DUTY -> emptyMap()
    }

    private fun createExpenseDataSets(
        selectedAccountId: Long?,
        accounts: List<Account>
    ): Map<DataSetSection, AccountDataSet> = mapOf(
        DataSetSection.FROM to
                AccountDataSet(
                    type = DataSetType.ASSET,
                    accounts = accounts.filter { it.type == AccountType.ASSET },
                    selectedAccountId = selectedAccountId
                ),
        DataSetSection.TO to
                AccountDataSet(
                    type = DataSetType.CATEGORY,
                    accounts = accounts.filter { it.type == AccountType.LIABILITY }
                )
    )

    private fun createIncomeDataSets(
        selectedAccountId: Long?,
        accounts: List<Account>
    ): Map<DataSetSection, AccountDataSet> = mapOf(
        DataSetSection.FROM to
                AccountDataSet(
                    type = DataSetType.CATEGORY,
                    accounts = accounts.filter { it.type == AccountType.INCOME }
                ),
        DataSetSection.TO to
                AccountDataSet(
                    type = DataSetType.ASSET,
                    accounts = accounts.filter { it.type == AccountType.ASSET },
                    selectedAccountId = selectedAccountId
                )
    )

    private fun createSendDataSets(
        selectedAccountId: Long?,
        accounts: List<Account>
    ): Map<DataSetSection, AccountDataSet> = mapOf(
        DataSetSection.FROM to
                AccountDataSet(
                    type = DataSetType.ASSET,
                    accounts = accounts.filter { it.type == AccountType.ASSET },
                    selectedAccountId = selectedAccountId
                ),
        DataSetSection.TO to
                AccountDataSet(
                    type = DataSetType.ASSET,
                    accounts = accounts.filter { it.type == AccountType.ASSET }
                ),
    )
}
