package com.harper.capital.overview

import com.harper.capital.overview.asset.domain.AddAssetUseCase
import com.harper.capital.overview.asset.ui.AssetAddFragment
import com.harper.capital.overview.asset.ui.AssetAddViewModel
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

        scope<AssetAddFragment> {
            scoped { AddAssetUseCase(get()) }
            viewModel { AssetAddViewModel(get(), get()) }
        }
    }
