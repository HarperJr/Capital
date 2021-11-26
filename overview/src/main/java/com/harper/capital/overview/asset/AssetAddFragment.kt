package com.harper.capital.overview.asset

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.harper.core.ui.ComponentFragment

class AssetAddFragment : ComponentFragment<AssetAddViewModel>() {
    override val viewModel: AssetAddViewModel by injectViewModel()

    override fun content(): @Composable () -> Unit = {
        val state by viewModel.state.collectAsState()
    }

    companion object {

        fun newInstance(): AssetAddFragment = AssetAddFragment()
    }
}
