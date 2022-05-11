package com.harper.capital.liability.domain

import com.harper.capital.domain.model.Account
import com.harper.capital.repository.account.AccountRepository
import kotlinx.coroutines.coroutineScope

class AddLiabilityUseCase(private val accountRepository: AccountRepository) {

    suspend operator fun invoke(account: Account) = coroutineScope {
        accountRepository.insert(account)
    }
}
