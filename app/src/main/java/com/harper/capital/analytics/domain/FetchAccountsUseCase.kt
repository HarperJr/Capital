package com.harper.capital.analytics.domain

import com.harper.capital.domain.model.Account
import com.harper.capital.repository.account.AccountRepository
import kotlinx.coroutines.flow.Flow

class FetchAccountsUseCase(private val accountRepository: AccountRepository) {

    operator fun invoke(): Flow<List<Account>> = accountRepository.fetchAll()
}
