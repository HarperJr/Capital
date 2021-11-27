package com.harper.capital.overview.asset

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harper.capital.overview.R
import com.harper.core.component.ComposablePreview
import com.harper.core.component.MenuIcon
import com.harper.core.component.Toolbar
import com.harper.core.theme.CapitalIcons
import com.harper.core.theme.CapitalTheme
import com.harper.core.ui.ComponentFragment

class AssetAddFragment : ComponentFragment<AssetAddViewModel>() {
    override val viewModel: AssetAddViewModel by injectViewModel()

    override fun content(): @Composable () -> Unit = {
        val state by viewModel.state.collectAsState()
        Content()
    }

    companion object {

        fun newInstance(): AssetAddFragment = AssetAddFragment()
    }
}

@Composable
private fun Content() {
    Scaffold(topBar = { AssetAddTopBar() }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(CapitalTheme.colors.background)
        ) {

        }
    }
}

@Composable
private fun AssetAddTopBar() {
    Toolbar(
        title = {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = stringResource(id = R.string.add_asset_title),
                style = CapitalTheme.typography.title,
                color = CapitalTheme.colors.onBackground
            )
        },
        navigation = {
            MenuIcon(imageVector = CapitalIcons.ArrowBack)
        }
    )
}

@Preview
@Composable
private fun ContentLight() {
    ComposablePreview {
        Content()
    }
}

@Preview
@Composable
private fun ContentDark() {
    ComposablePreview(isDark = true) {
        Content()
    }
}
