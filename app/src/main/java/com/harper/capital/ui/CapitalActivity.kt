package com.harper.capital.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.google.accompanist.insets.ProvideWindowInsets
import com.harper.capital.R
import com.harper.capital.databinding.ActivityCapitalBinding
import com.harper.capital.navigation.GlobalNavigator
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.activityScope
import org.koin.androidx.viewmodel.ViewModelOwner
import org.koin.androidx.viewmodel.scope.viewModel

class CapitalActivity : AppCompatActivity() {
    private val scope by activityScope()
    private val viewModel by scope.viewModel<CapitalViewModel>(owner = {
        ViewModelOwner.from(this)
    })
    private val navigationHolder: NavigatorHolder by inject()

    private lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ProvideWindowInsets(consumeWindowInsets = false) {
                AndroidView(factory = { ActivityCapitalBinding.inflate(LayoutInflater.from(it)).root })
            }
        }
        navigator = createNavigator()
    }

    override fun onStart() {
        super.onStart()
        viewModel.start()
        navigationHolder.setNavigator(navigator)
    }

    override fun onStop() {
        navigationHolder.removeNavigator()
        super.onStop()
    }

    private fun createNavigator(): Navigator =
        GlobalNavigator(this, R.id.screen_container, supportFragmentManager)
}
