package com.harper.capital.transaction

import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.AccountType
import com.harper.capital.domain.model.TransactionType
import com.harper.capital.transaction.model.AccountDataSet
import com.harper.capital.transaction.model.DataSetSection
import com.harper.capital.transaction.model.DataSetType

class TransactionDataSetFactory {

    fun create(
        type: TransactionType,
        selectedAssetId: Long?,
        accounts: List<Account>
    ): List<AccountDataSet> = when (type) {
        TransactionType.EXPENSE -> createExpenseDataSets(selectedAssetId, accounts)
        TransactionType.INCOME -> createIncomeDataSets(selectedAssetId, accounts)
        TransactionType.SEND -> createSendDataSets(selectedAssetId, accounts)
        TransactionType.DUTY -> emptyList()
    }

    private fun createExpenseDataSets(
        selectedAssetId: Long?,
        accounts: List<Account>
    ): List<AccountDataSet> = listOf(
        AccountDataSet(
            type = DataSetType.ASSET,
            section = DataSetSection.FROM,
            accounts = accounts.filter { it.type == AccountType.ASSET },
            selectedAccountId = selectedAssetId
        ),
        AccountDataSet(
            type = DataSetType.CATEGORY,
            section = DataSetSection.TO,
            accounts = accounts.filter { it.type == AccountType.LIABILITY }
        )
    )

    private fun createIncomeDataSets(
        selectedAssetId: Long?,
        accounts: List<Account>
    ): List<AccountDataSet> = listOf(
        AccountDataSet(
            type = DataSetType.CATEGORY,
            section = DataSetSection.FROM,
            accounts = accounts.filter { it.type == AccountType.INCOME }
        ),
        AccountDataSet(
            type = DataSetType.ASSET,
            section = DataSetSection.TO,
            accounts = accounts.filter { it.type == AccountType.ASSET },
            selectedAccountId = selectedAssetId
        )
    )

    private fun createSendDataSets(
        selectedAssetId: Long?,
        accounts: List<Account>
    ): List<AccountDataSet> = listOf(
        AccountDataSet(
            type = DataSetType.ASSET,
            section = DataSetSection.FROM,
            accounts = accounts.filter { it.type == AccountType.ASSET },
            selectedAccountId = selectedAssetId
        ),
        AccountDataSet(
            type = DataSetType.ASSET,
            section = DataSetSection.TO,
            accounts = accounts.filter { it.type == AccountType.ASSET }
        ),
    )
}
