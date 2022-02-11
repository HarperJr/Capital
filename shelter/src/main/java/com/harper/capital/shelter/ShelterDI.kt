package com.harper.capital.shelter

import com.harper.capital.shelter.details.ShelterDetailsViewModel
import com.harper.capital.shelter.domain.FetchAssetsUseCase
import com.harper.capital.shelter.main.ShelterMainViewModel
import com.harper.capital.shelter.model.ShelterDetailParams
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val shelterModule
    get() = module {

        factory { FetchAssetsUseCase(get()) }

        viewModel { ShelterMainViewModel(get()) }

        viewModel { (params: ShelterDetailParams) -> ShelterDetailsViewModel(params) }
    }
