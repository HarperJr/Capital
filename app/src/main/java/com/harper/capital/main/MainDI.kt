package com.harper.capital.main

import com.harper.capital.main.domain.FetchAssetsUseCase
import com.harper.capital.main.domain.FetchSummaryUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule
    get() = module {
        factory { FetchAssetsUseCase(get()) }

        factory { FetchSummaryUseCase(get()) }

        viewModel { MainViewModel(get(), get(), get()) }
    }
