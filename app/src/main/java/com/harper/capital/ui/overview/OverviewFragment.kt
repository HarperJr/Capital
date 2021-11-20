package com.harper.capital.ui.overview

import androidx.compose.runtime.Composable
import com.harper.core.ui.ComponentFragment

class OverviewFragment : ComponentFragment<OverviewViewModel>() {
    override val viewModel: OverviewViewModel by injectViewModel()

    override fun content(): @Composable () -> Unit = {

    }

    companion object {

        fun newInstance(): OverviewFragment = OverviewFragment()
    }
}