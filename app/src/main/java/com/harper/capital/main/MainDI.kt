package com.harper.capital.main

import com.harper.capital.asset.AssetAddFragment
import com.harper.capital.asset.AssetManageViewModel
import com.harper.capital.asset.domain.AddAssetUseCase
import com.harper.capital.main.domain.FetchAssetsUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val overviewModule
    get() = module {

        scope<MainFragment> {
            scoped { FetchAssetsUseCase(get()) }
            viewModel { MainViewModel(get(), get()) }
        }

        scope<AssetAddFragment> {
            scoped { AddAssetUseCase(get()) }
            viewModel { AssetManageViewModel(get(), get()) }
        }
    }
