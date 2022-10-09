package com.harper.capital.shelter.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.harper.capital.shelter.core.ComposableViewModel
import com.harper.capital.shelter.model.ShelterEvent
import com.harper.capital.shelter.model.ShelterState
import com.harper.core.component.CButtonCommon
import com.harper.core.component.CHorizontalSpacer
import com.harper.core.component.CScaffold
import com.harper.core.component.CTextField
import com.harper.core.component.CToolbarCommon
import com.harper.core.theme.CapitalTheme

@Composable
fun MainScreen(viewModel: ComposableViewModel<ShelterState, ShelterEvent>, onDetailsClick: (String) -> Unit, onExampleClick: () -> Unit) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.onFirstCompose()
    }
    CScaffold(
        topBar = {
            CToolbarCommon(
                title = "Shelter",
                onNavigationClick = { viewModel.onEvent(ShelterEvent.BackClick) }
            )
        }
    ) {
        Column(modifier = Modifier.padding(horizontal = CapitalTheme.dimensions.side)) {
            CHorizontalSpacer(height = CapitalTheme.dimensions.side)
            CTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.text,
                title = {
                    Text(text = "Text field")
                },
                onValueChange = { state.text = it }
            )
            CHorizontalSpacer(height = CapitalTheme.dimensions.side)
            LazyColumn {
                items(state.assetStates) { assetState ->
                    Card(modifier = Modifier
                        .clickable { onDetailsClick.invoke(assetState.account.name) }
                        .padding(vertical = CapitalTheme.dimensions.side)
                    ) {
                        Column(modifier = Modifier.padding(CapitalTheme.dimensions.medium)) {
                            Text(text = assetState.account.name)
                            CHorizontalSpacer(height = CapitalTheme.dimensions.side)
                        }
                    }
                }
            }
            CButtonCommon(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = CapitalTheme.dimensions.side),
                text = "Example Screen"
            ) { onExampleClick.invoke() }
        }
    }
}
