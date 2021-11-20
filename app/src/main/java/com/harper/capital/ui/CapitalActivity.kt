package com.harper.capital.ui

import android.os.Bundle
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.FragmentActivity
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CapitalActivity : FragmentActivity() {
    private val viewModel by viewModel<CapitalViewModel>()
    private val navigationHolder: NavigatorHolder by inject()
    private lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navHostView = FrameLayout(this)
            .apply { id = NAV_HOST_VIEW_ID }
        setContentView(
            navHostView,
            FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
        )
        navigator = createNavigator(navHostView)
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

    private fun createNavigator(navHostView: ViewGroup): Navigator =
        AppNavigator(this, navHostView.id, supportFragmentManager)

    companion object {
        private const val NAV_HOST_VIEW_ID = 0x162432
    }
}
