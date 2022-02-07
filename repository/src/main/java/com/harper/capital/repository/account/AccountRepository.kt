package com.harper.capital.repository.account

import com.harper.capital.domain.model.Account
import com.harper.capital.domain.model.AccountType
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    suspend fun insert(account: Account)

    suspend fun fetchById(id: Long): Account

    suspend fun update(account: Account)

    fun fetchByType(type: AccountType): Flow<List<Account>>

    fun fetchAll(): Flow<List<Account>>
}
