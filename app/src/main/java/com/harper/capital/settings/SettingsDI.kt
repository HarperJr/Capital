package com.harper.capital.settings

import com.harper.capital.settings.domain.ChangeAccountPresentationUseCase
import com.harper.capital.settings.domain.ChangeColorThemeUseCase
import com.harper.capital.settings.domain.ChangeCurrencyUseCase
import com.harper.capital.settings.domain.FetchSettingsUseCase
import com.harper.capital.settings.domain.GetSettingsUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val settingsModule
    get() = module {
        factory { GetSettingsUseCase(get()) }

        factory { FetchSettingsUseCase(get()) }

        factory { ChangeColorThemeUseCase(get()) }

        factory { ChangeCurrencyUseCase(get()) }

        factory { ChangeAccountPresentationUseCase(get()) }

        viewModel { SettingsViewModel(get(), get(), get(), get(), get()) }
    }
