package com.harper.capital.accounts

import com.harper.capital.analytics.domain.FetchAccountsUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val accountsModule
    get() = module {
        factory { FetchAccountsUseCase(get()) }

        viewModel { AccountsViewModel(get(), get()) }
    }
