package com.harper.capital

import com.github.terrakok.cicerone.Cicerone
import com.harper.capital.navigation.GlobalRouter
import com.harper.capital.navigation.GlobalRouterImpl
import com.harper.capital.ui.CapitalActivity
import com.harper.capital.ui.CapitalViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule
    get() = module {

        single { Cicerone.create(GlobalRouterImpl()) }

        single<GlobalRouter> { get<Cicerone<GlobalRouterImpl>>().router }

        single { get<Cicerone<GlobalRouterImpl>>().getNavigatorHolder() }

        scope<CapitalActivity> {
            viewModel { CapitalViewModel(get()) }
        }
    }
