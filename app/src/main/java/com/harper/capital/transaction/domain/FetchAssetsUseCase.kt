package com.harper.capital.transaction.domain

import com.harper.capital.domain.model.AccountType
import com.harper.capital.repository.account.AccountRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

class FetchAssetsUseCase(private val accountRepository: AccountRepository) {

    suspend operator fun invoke() = coroutineScope {
        accountRepository.fetchAll()
            .map { it.filter { account -> account.type != AccountType.LIABILITY } }
    }
}
