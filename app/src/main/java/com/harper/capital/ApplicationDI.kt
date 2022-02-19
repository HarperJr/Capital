package com.harper.capital

import android.content.Context
import com.github.terrakok.cicerone.Cicerone
import com.harper.capital.asset.AssetManageFragment
import com.harper.capital.asset.AssetManageViewModel
import com.harper.capital.asset.domain.AddAssetUseCase
import com.harper.capital.asset.domain.UpdateAssetUseCase
import com.harper.capital.auth.signin.SignInViewModel
import com.harper.capital.main.MainViewModel
import com.harper.capital.main.domain.FetchAssetsUseCase
import com.harper.capital.main.domain.FetchSummaryUseCase
import com.harper.capital.navigation.GlobalRouter
import com.harper.capital.navigation.GlobalRouterImpl
import com.harper.capital.prefs.SharedPrefs
import com.harper.capital.transaction.manage.domain.FetchAssetUseCase
import com.harper.capital.ui.CapitalActivity
import com.harper.capital.ui.CapitalViewModel
import com.harper.capital.ui.ColorThemeProvider
import org.koin.androidx.viewmodel.dsl.viewModel
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

        scope<CapitalActivity> {
            viewModel { CapitalViewModel(get()) }
        }

        factory { FetchAssetsUseCase(get()) }

        factory { FetchSummaryUseCase(get()) }

        factory { UpdateAssetUseCase(get()) }

        factory { FetchAssetUseCase(get()) }

        factory { AddAssetUseCase(get()) }

        viewModel {
            SignInViewModel(get())
        }

        viewModel {
            MainViewModel(get(), get(), get())
        }

        viewModel { (params: AssetManageFragment.Params) ->
            AssetManageViewModel(params, get(), get(), get(), get())
        }
    }
