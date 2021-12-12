package com.harper.capital.overview

import com.harper.capital.asset.AssetAddFragment
import com.harper.capital.asset.AssetAddViewModel
import com.harper.capital.asset.domain.AddAssetUseCase
import com.harper.capital.overview.domain.FetchAssetsUseCase
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
