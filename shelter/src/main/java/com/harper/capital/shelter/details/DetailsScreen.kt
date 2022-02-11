package com.harper.capital.shelter.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.harper.capital.shelter.core.ComposableViewModel
import com.harper.capital.shelter.model.ShelterDetailsEvent
import com.harper.capital.shelter.model.ShelterDetailsState
import com.harper.core.component.CScaffold
import com.harper.core.component.CToolbarCommon

@Composable
fun DetailsScreen(viewModel: ComposableViewModel<ShelterDetailsState, ShelterDetailsEvent>, onBackClick: () -> Unit) {
    val state by viewModel.state.collectAsState()
    CScaffold(topBar = { CToolbarCommon(title = state.name, onNavigationClick = { onBackClick.invoke() }) }) {

    }
}
