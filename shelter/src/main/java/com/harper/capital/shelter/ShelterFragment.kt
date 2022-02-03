package com.harper.capital.shelter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.harper.capital.shelter.core.ComposableFragment
import com.harper.capital.shelter.core.ScreenLayout
import com.harper.capital.shelter.model.ShelterEvent
import com.harper.capital.shelter.model.ShelterState
import com.harper.core.component.CAmountTextField
import com.harper.core.component.CHorizontalSpacer
import com.harper.core.component.CScaffold
import com.harper.core.component.CTextField
import com.harper.core.component.CToolbarCommon
import com.harper.core.theme.CapitalTheme

class ShelterFragment : ComposableFragment<ShelterViewModel>() {
    override val viewModel: ShelterViewModel by injectViewModel()

    @Composable
    override fun ScreenContent() {
        ScreenLayout {
            val state by viewModel.state.collectAsState()
            ShelterScreen(state) {
                viewModel.onEvent(it)
            }
        }
    }

    companion object {

        fun newInstance(): ShelterFragment = ShelterFragment()
    }
}

@Composable
private fun ShelterScreen(state: ShelterState, onEvent: (ShelterEvent) -> Unit) {
    CScaffold(
        topBar = {
            CToolbarCommon(
                title = "Shelter",
                onNavigationClick = { onEvent(ShelterEvent.BackClick) }
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
                    Card(modifier = Modifier.padding(vertical = CapitalTheme.dimensions.side)) {
                        Column(modifier = Modifier.padding(CapitalTheme.dimensions.medium)) {
                            Text(text = assetState.asset.name)
                            CHorizontalSpacer(height = CapitalTheme.dimensions.side)
                            CAmountTextField(
                                modifier = Modifier.fillMaxWidth(),
                                amount = assetState.amount,
                                title = {
                                    Text(text = "Text field")
                                },
                                onValueChange = { assetState.amount = it }
                            )
                        }
                    }
                }
            }
        }
    }
}
