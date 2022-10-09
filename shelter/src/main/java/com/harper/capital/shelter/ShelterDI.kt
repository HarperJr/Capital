package com.harper.capital.shelter

import com.harper.capital.shelter.details.ShelterDetailsViewModel
import com.harper.capital.shelter.domain.FetchAssetsUseCase
import com.harper.capital.shelter.main.ShelterMainViewModel
import com.harper.capital.shelter.model.ShelterDetailParams
import com.harper.capital.shelter.v2.domain.ExampleInteractor
import com.harper.capital.shelter.v2.data.ExampleDaoImpl
import com.harper.capital.shelter.v2.data.ExampleRepository
import com.harper.capital.shelter.v2.ui.ExampleStateManager
import com.harper.capital.shelter.v2.ui.ExampleViewModel
import com.harper.capital.shelter.v2.ui.SubExampleStateManager
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val shelterModule
    get() = module {

        factory { FetchAssetsUseCase(get()) }

        viewModel { ShelterMainViewModel(get()) }

        viewModel { (params: ShelterDetailParams) -> ShelterDetailsViewModel(params) }

        viewModel { ExampleViewModel() }

        scope(named(ShelterScreens.EXAMPLE)) {
            scoped { ExampleDaoImpl() }
            scoped { ExampleRepository(get()) }
            scoped { ExampleInteractor(get()) }
            scoped { SubExampleStateManager() }
            scoped { ExampleStateManager() }
        }
    }
