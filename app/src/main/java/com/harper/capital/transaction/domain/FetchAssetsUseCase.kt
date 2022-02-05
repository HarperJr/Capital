package com.harper.capital.transaction.domain

import com.harper.capital.repository.AccountRepository
import kotlinx.coroutines.coroutineScope

class FetchAssetsUseCase(private val accountRepository: AccountRepository) {

    suspend operator fun invoke() = coroutineScope {
        accountRepository.fetchAll()
    }
}
