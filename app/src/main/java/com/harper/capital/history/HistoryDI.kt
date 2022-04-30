package com.harper.capital.history

import com.harper.capital.history.domain.FetchTransactionsUseCase
import com.harper.capital.transaction.manage.domain.FetchAccountUseCase
import com.harper.capital.transaction.manage.domain.FetchCurrencyRatesUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val historyModule
    get() = module {
        factory { FetchTransactionsUseCase(get()) }

        factory { FetchCurrencyRatesUseCase(get()) }

        factory { FetchAccountUseCase(get()) }

        viewModel { (params: HistoryListParams) ->
            HistoryListViewModel(params, get(), get(), get(), get(), get())
        }
    }
