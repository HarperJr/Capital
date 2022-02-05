package com.harper.capital.shelter.domain

import com.harper.capital.domain.model.Account
import com.harper.capital.repository.AccountRepository
import kotlinx.coroutines.flow.Flow

class FetchAssetsUseCase(private val accountRepository: AccountRepository) {

    operator fun invoke(): Flow<List<Account>> = accountRepository.fetchAll()
}
