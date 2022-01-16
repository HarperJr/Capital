package com.harper.capital.main

import com.harper.capital.asset.AssetManageFragment
import com.harper.capital.asset.AssetManageViewModel
import com.harper.capital.asset.domain.AddAssetUseCase
import com.harper.capital.main.domain.FetchAssetsUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule
    get() = module {

        scope<MainFragment> {
            scoped { FetchAssetsUseCase(get()) }
            viewModel { MainViewModel(get(), get()) }
        }

        scope<AssetManageFragment> {
            scoped { AddAssetUseCase(get()) }
            viewModel { AssetManageViewModel(get(), get()) }
        }
    }
