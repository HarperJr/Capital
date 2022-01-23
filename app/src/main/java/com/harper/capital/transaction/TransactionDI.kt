package com.harper.capital.transaction

import com.harper.capital.transaction.domain.FetchAssetsUseCase
import com.harper.capital.transaction.manage.TransactionManageFragment
import com.harper.capital.transaction.manage.TransactionManageViewModel
import com.harper.capital.transaction.manage.domain.FetchAssetUseCase
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
            scoped { FetchAssetUseCase(get()) }
            viewModel { (params: TransactionManageFragment.Params) ->
                TransactionManageViewModel(params, get(), get())
            }
        }
    }
