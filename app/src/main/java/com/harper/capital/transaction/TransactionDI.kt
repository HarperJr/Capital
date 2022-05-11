package com.harper.capital.transaction

import com.harper.capital.transaction.domain.AddDebtContactUseCase
import com.harper.capital.transaction.domain.FetchAssetsUseCase
import com.harper.capital.transaction.domain.FetchLiabilitiesUseCase
import com.harper.capital.transaction.manage.TransactionManageParams
import com.harper.capital.transaction.manage.TransactionManageViewModel
import com.harper.capital.transaction.manage.domain.AddTransactionUseCase
import com.harper.capital.transaction.manage.domain.FetchAccountUseCase
import com.harper.capital.transaction.manage.domain.FetchCurrencyRatesUseCase
import com.harper.capital.transaction.manage.domain.FetchTransactionUseCase
import com.harper.capital.transaction.manage.domain.UpdateTransactionUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val transactionModule
    get() = module {
        factory { FetchAssetsUseCase(get()) }

        factory { FetchCurrencyRatesUseCase(get()) }

        factory { FetchLiabilitiesUseCase(get(), get()) }

        viewModel { (params: TransactionParams) ->
            TransactionViewModel(params, get(), get(), get())
        }

        factory { AddTransactionUseCase(get()) }

        factory { FetchAccountUseCase(get()) }

        factory { FetchTransactionUseCase(get()) }

        factory { UpdateTransactionUseCase(get()) }

        viewModel { (params: TransactionManageParams) ->
            TransactionManageViewModel(params, get(), get(), get(), get(), get(), get())
        }
    }
