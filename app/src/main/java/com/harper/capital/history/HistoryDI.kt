package com.harper.capital.history

import com.harper.capital.history.domain.FetchTransactionsUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val historyModule
    get() = module {

        scope<HistoryListFragment> {
            scoped { FetchTransactionsUseCase(get()) }
            viewModel { (params: HistoryListFragment.Params) ->
                HistoryListViewModel(params, get(), get())
            }
        }
    }
