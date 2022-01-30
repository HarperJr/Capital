package com.harper.capital.shelter

import com.harper.capital.shelter.domain.FetchAssetsUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val shelterModule
    get() = module {

        scope<ShelterFragment> {
            scoped { FetchAssetsUseCase(get()) }
            viewModel { ShelterViewModel(get()) }
        }
    }
