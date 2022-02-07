package com.harper.capital.repository

import com.harper.capital.repository.account.AccountRepository
import com.harper.capital.repository.account.AccountRepositoryImpl
import com.harper.capital.repository.transaction.TransactionRepository
import com.harper.capital.repository.transaction.TransactionRepositoryImpl
import org.koin.dsl.module

val repositoryModule
    get() = module {

        factory<AccountRepository> { AccountRepositoryImpl(get(), get(), get()) }

        factory<TransactionRepository> { TransactionRepositoryImpl(get(), get()) }
    }