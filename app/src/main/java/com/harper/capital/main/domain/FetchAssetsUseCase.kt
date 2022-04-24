package com.harper.capital.main.domain

import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.AccountType
import com.harper.capital.repository.account.AccountRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow

class FetchAssetsUseCase(private val accountRepository: AccountRepository) {

    operator fun invoke(): Flow<List<Account>> {
        return accountRepository.fetchByType(AccountType.ASSET)
    }
}
