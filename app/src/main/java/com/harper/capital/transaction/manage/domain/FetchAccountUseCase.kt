package com.harper.capital.transaction.manage.domain

import com.harper.capital.repository.account.AccountRepository
import kotlinx.coroutines.coroutineScope

class FetchAccountUseCase(private val accountRepository: AccountRepository) {

    suspend operator fun invoke(id: Long) = coroutineScope {
        accountRepository.fetchById(id)
    }
}
