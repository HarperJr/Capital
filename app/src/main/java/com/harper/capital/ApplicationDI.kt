package com.harper.capital

import com.github.terrakok.cicerone.Cicerone
import com.harper.capital.navigation.GlobalRouter
import com.harper.capital.navigation.GlobalRouterImpl
import com.harper.capital.prefs.SettingsManager
import org.koin.dsl.module

val appModule
    get() = module {

        single { SettingsManager(get()) }

        single { Cicerone.create(GlobalRouterImpl()) }

        single<GlobalRouter> { get<Cicerone<GlobalRouterImpl>>().router }

        single { get<Cicerone<GlobalRouterImpl>>().getNavigatorHolder() }
    }
