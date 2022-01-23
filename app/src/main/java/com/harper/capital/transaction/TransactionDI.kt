package com.harper.capital.transaction

import com.harper.capital.transaction.domain.FetchAssetsUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val transactionModule
    get() = module {

        scope<TransactionFragment> {
            scoped { FetchAssetsUseCase(get()) }
            viewModel { (params: TransactionFragment.Params) ->
                TransactionViewModel(params, get(), get())
            }
        }
    }
