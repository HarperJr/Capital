package com.harper.capital.main

import com.harper.capital.main.domain.FetchAssetsUseCase
import com.harper.capital.main.domain.FetchFavoriteTransactionsUseCase
import com.harper.capital.main.domain.FetchSummaryUseCase
import com.harper.capital.main.domain.UpdateCurrenciesUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule
    get() = module {
        factory { UpdateCurrenciesUseCase(get(), get()) }

        factory { FetchAssetsUseCase(get()) }

        factory { FetchSummaryUseCase(get(), get(), get()) }

        factory { FetchFavoriteTransactionsUseCase(get()) }

        viewModel { MainViewModel(get(), get(), get(), get(), get(), get()) }
    }
