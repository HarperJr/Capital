package com.harper.capital.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.view.WindowCompat
import com.github.terrakok.cicerone.NavigatorHolder
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.harper.capital.BuildConfig
import com.harper.capital.analytics.analytics
import com.harper.capital.asset.assetManage
import com.harper.capital.auth.signin.signIn
import com.harper.capital.category.categoryManage
import com.harper.capital.history.historyList
import com.harper.capital.main.main
import com.harper.capital.navigation.ScreenKey
import com.harper.capital.navigation.ComposableNavigator
import com.harper.capital.settings.settings
import com.harper.capital.transaction.manage.transactionManage
import com.harper.capital.transaction.transaction
import com.harper.capital.ui.model.ColorTheme
import com.harper.core.theme.CapitalColors
import com.harper.core.theme.CapitalTheme
import kotlinx.coroutines.flow.map
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject

@OptIn(ExperimentalAnimationApi::class)
class CapitalActivity : ComponentActivity() {
    private val navigator: ComposableNavigator = ComposableNavigator()
    private val navigationHolder: NavigatorHolder by inject()

    @SuppressLint("FlowOperatorInvokedInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ProvideWindowInsets {
                val isDarkThemeState by get<ColorThemeProvider>().colorThemeFlow
                    .map { ColorTheme.valueOf(it) == ColorTheme.DARK }
                    .collectAsState(initial = false)
                val systemUiController = rememberSystemUiController()
                val useDarkIcons = CapitalTheme.colors.isLight
                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = CapitalColors.Transparent,
                        darkIcons = useDarkIcons
                    )
                }
                CapitalTheme(isDark = isDarkThemeState) {
                    val navController = rememberAnimatedNavController()
                    DisposableEffect(Unit) {
                        navigator.attachNavController(navController)
                        onDispose {
                            navigator.detachNavController()
                        }
                    }
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = (if (BuildConfig.DEBUG) ScreenKey.SIGN_IN else ScreenKey.MAIN).route
                    ) {
                        main()
                        signIn()
                        assetManage()
                        categoryManage()
                        transaction()
                        transactionManage()
                        historyList()
                        analytics()
                        settings()
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        navigationHolder.setNavigator(navigator)
    }

    override fun onStop() {
        navigationHolder.removeNavigator()
        super.onStop()
    }
}
