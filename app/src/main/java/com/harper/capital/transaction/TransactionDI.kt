package com.harper.capital.transaction

import com.harper.capital.transaction.domain.FetchAssetsUseCase
import com.harper.capital.transaction.manage.TransactionManageFragment
import com.harper.capital.transaction.manage.TransactionManageViewModel
import com.harper.capital.transaction.manage.domain.AddTransactionUseCase
import com.harper.capital.transaction.manage.domain.FetchAssetUseCase
import com.harper.capital.transaction.manage.domain.FetchTransactionUseCase
import com.harper.capital.transaction.manage.domain.UpdateTransactionUseCase
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

        scope<TransactionManageFragment> {
            scoped { AddTransactionUseCase(get()) }
            scoped { FetchAssetUseCase(get()) }
            scoped { FetchTransactionUseCase(get()) }
            scoped { UpdateTransactionUseCase(get()) }
            viewModel { (params: TransactionManageFragment.Params) ->
                TransactionManageViewModel(params, get(), get(), get(), get(), get())
            }
        }
    }
