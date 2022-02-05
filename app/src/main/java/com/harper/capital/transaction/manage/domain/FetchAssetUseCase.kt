package com.harper.capital.transaction.manage.domain

import com.harper.capital.repository.AccountRepository
import kotlinx.coroutines.coroutineScope

class FetchAssetUseCase(private val accountRepository: AccountRepository) {

    suspend operator fun invoke(id: Long) = coroutineScope {
        accountRepository.fetchById(id)
    }
}
