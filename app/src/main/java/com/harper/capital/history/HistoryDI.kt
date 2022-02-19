package com.harper.capital.history

import com.harper.capital.history.domain.FetchTransactionsUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val historyModule
    get() = module {
        factory { FetchTransactionsUseCase(get()) }

        viewModel { (params: HistoryListParams) ->
            HistoryListViewModel(params, get(), get())
        }
    }
