package com.harper.capital.asset

import com.harper.capital.asset.domain.AddAssetUseCase
import com.harper.capital.asset.domain.UpdateAssetUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val assetModule
    get() = module {
        factory { AddAssetUseCase(get()) }

        factory { UpdateAssetUseCase(get()) }

        viewModel { (params: AssetManageParams) ->
            AssetManageViewModel(params, get(), get(), get(), get())
        }
    }
