package com.harper.capital.settings

import com.harper.capital.settings.domain.ChangeColorThemeUseCase
import com.harper.capital.settings.domain.GetColorThemeUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val settingsModule
    get() = module {
        scope<SettingsFragment> {
            scoped { GetColorThemeUseCase(get()) }
            scoped { ChangeColorThemeUseCase(get()) }
            viewModel { SettingsViewModel(get(), get(), get()) }
        }
    }
