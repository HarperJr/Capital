package com.harper.capital.ui.di

import com.github.terrakok.cicerone.Cicerone
import com.harper.capital.ui.navigation.GlobalRouter
import org.koin.dsl.module

val application
    get() = module {

        single { Cicerone.create(GlobalRouter()) }

        single { get<Cicerone<GlobalRouter>>().router }

        single { get<Cicerone<GlobalRouter>>().getNavigatorHolder() }
    }
