package com.harper.capital

import android.content.Context
import com.github.terrakok.cicerone.Cicerone
import com.harper.capital.navigation.GlobalRouter
import com.harper.capital.navigation.GlobalRouterImpl
import com.harper.capital.prefs.SharedPrefs
import com.harper.capital.ui.ColorThemeProvider
import org.koin.dsl.module

private const val CAPITAL_SHARED_PREFERENCES = "capital_shared_preferences"

val appModule
    get() = module {

        single {
            SharedPrefs(
                sharedPrefs = get<Context>()
                    .getSharedPreferences(CAPITAL_SHARED_PREFERENCES, Context.MODE_PRIVATE)
            )
        }

        single { ColorThemeProvider(get()) }

        single { Cicerone.create(GlobalRouterImpl()) }

        single<GlobalRouter> { get<Cicerone<GlobalRouterImpl>>().router }

        single { get<Cicerone<GlobalRouterImpl>>().getNavigatorHolder() }
    }
