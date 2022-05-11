package com.harper.capital.accounts

import com.harper.capital.accounts.model.AccountDataSet
import com.harper.capital.accounts.model.DataSetSection
import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.AccountMetadata
import com.harper.capital.domain.model.AccountType

class AccountDataSetFactory {

    fun create(accounts: List<Account>): Map<DataSetSection, AccountDataSet> = createDataSets(accounts)

    private fun createDataSets(accounts: List<Account>): Map<DataSetSection, AccountDataSet> = mapOf(
        DataSetSection.INCOME to AccountDataSet(
            type = AccountType.INCOME,
            accounts = accounts.filter { it.type == AccountType.INCOME }
        ),
        DataSetSection.ASSET to AccountDataSet(
            type = AccountType.ASSET,
            accounts = accounts.filter { it.type == AccountType.ASSET }
        ),
        DataSetSection.LIABILITY to AccountDataSet(
            type = AccountType.LIABILITY,
            accounts = accounts.filter { it.type == AccountType.LIABILITY && it.metadata !is AccountMetadata.Debt }
        ),
        DataSetSection.DEBT to AccountDataSet(
            type = AccountType.LIABILITY,
            accounts = accounts.filter { it.type == AccountType.LIABILITY && it.metadata is AccountMetadata.Debt }
        )
    )
}
