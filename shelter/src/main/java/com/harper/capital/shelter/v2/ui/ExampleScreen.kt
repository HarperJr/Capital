package com.harper.capital.shelter.v2.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.harper.capital.shelter.v2.ui.model.ExampleEvent
import com.harper.core.component.CLoaderLayout
import com.harper.core.component.CScaffold
import com.harper.core.component.CToolbarCommon
import com.harper.core.theme.CapitalTheme

@Composable
internal fun ExampleScreen(viewModel: ExampleViewModel) {
    val state by viewModel.state.collectAsState()
    val error by viewModel.errorState.collectAsState()
    val isLoading by viewModel.loadingState.collectAsState()

    CScaffold(topBar = { CToolbarCommon(title = "Example", onNavigationClick = { viewModel.postEvent(ExampleEvent.BackClick) }) }) {
        CLoaderLayout(isLoading = isLoading) {
            Column {
                SubView(modifier = Modifier.fillMaxWidth(), component = viewModel.subComponent)
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    items(state.dataList) {
                        Box(
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .height(CapitalTheme.dimensions.largest)
                                .background(color = CapitalTheme.colors.onBackground)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SubView(component: ProductsComponent, modifier: Modifier = Modifier) {
    val state by component.state.collectAsState()
    val error by component.errorState.collectAsState()
    val isLoading by component.loadingState.collectAsState()

}
