package com.harper.capital.transaction

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val transactionModule
    get() = module {

        scope<TransactionFragment> {
            viewModel { (params: TransactionFragment.Params) ->
                TransactionViewModel(params, get())
            }
        }
    }
