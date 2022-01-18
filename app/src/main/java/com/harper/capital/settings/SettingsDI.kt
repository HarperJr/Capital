package com.harper.capital.settings

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val settingsModule
    get() = module {
        scope<SettingsFragment> {
            viewModel { SettingsViewModel(get()) }
        }
    }
