package com.harper.capital.analytics

import com.harper.capital.analytics.domain.FetchAccountsUseCase
import com.harper.capital.analytics.domain.FetchBalancePartitionsByPeriodUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val analyticsModule
    get() = module {
        factory {
            FetchAccountsUseCase(get())
        }

        factory {
            FetchBalancePartitionsByPeriodUseCase(get())
        }

        viewModel { (sectionIndex: Int) ->
            AnalyticsViewModel(sectionIndex, get(), get(), get())
        }
    }
