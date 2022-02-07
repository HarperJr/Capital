package com.harper.capital.category.domain

import com.harper.capital.domain.model.Account
import com.harper.capital.repository.account.AccountRepository
import kotlinx.coroutines.coroutineScope

class AddCategoryUseCase(private val accountRepository: AccountRepository) {

    suspend operator fun invoke(account: Account) = coroutineScope {
        accountRepository.insert(account)
    }
}
