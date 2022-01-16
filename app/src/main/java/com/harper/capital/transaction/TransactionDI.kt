package com.harper.capital.transaction

import com.harper.capital.main.domain.FetchAssetsUseCase
import com.harper.capital.transaction.domain.FetchCategoriesUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val transactionModule
    get() = module {

        scope<TransactionFragment> {
            scoped { FetchAssetsUseCase(get()) }
            scoped { FetchCategoriesUseCase() }
            viewModel { (params: TransactionFragment.Params) ->
                TransactionViewModel(params, get(), get(), get())
            }
        }
    }
