package com.harper.capital.overview

import com.harper.capital.overview.domain.FetchAssetsUseCase
import com.harper.capital.overview.ui.OverviewFragment
import com.harper.capital.overview.ui.OverviewViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val overviewModule
    get() = module {

        scope<OverviewFragment> {
            scoped { FetchAssetsUseCase(get()) }
            viewModel { OverviewViewModel(get(), get()) }
        }
    }
