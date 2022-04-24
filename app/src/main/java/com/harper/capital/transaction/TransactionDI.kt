package com.harper.capital.transaction

import com.harper.capital.transaction.domain.FetchAssetsUseCase
import com.harper.capital.transaction.manage.TransactionManageParams
import com.harper.capital.transaction.manage.TransactionManageViewModel
import com.harper.capital.transaction.manage.domain.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val transactionModule
    get() = module {
        factory { FetchAssetsUseCase(get()) }

        factory { FetchCurrencyRatesUseCase(get()) }

        viewModel { (params: TransactionParams) ->
            TransactionViewModel(params, get(), get())
        }

        factory { AddTransactionUseCase(get()) }

        factory { FetchAssetUseCase(get()) }

        factory { FetchTransactionUseCase(get()) }

        factory { UpdateTransactionUseCase(get()) }

        viewModel { (params: TransactionManageParams) ->
            TransactionManageViewModel(params, get(), get(), get(), get(), get(), get())
        }
    }
