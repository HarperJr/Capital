package com.harper.capital.overview.asset.ui

import com.harper.capital.overview.asset.ui.model.AssetAddEvent
import com.harper.capital.overview.asset.ui.model.AssetAddState
import com.harper.core.ui.ComponentViewModel
import com.harper.core.ui.EventObserver

class AssetAddViewModel : ComponentViewModel<AssetAddState>(
    defaultState = AssetAddState.Data()
), EventObserver<AssetAddEvent> {

    override fun onEvent(event: AssetAddEvent) {
        when (event) {
            is AssetAddEvent.ColorSelect -> onColorSelect(event)
            is AssetAddEvent.Apply -> {}
        }
    }

    private fun onColorSelect(event: AssetAddEvent.ColorSelect) {
        mutateReducedState {
            forState<AssetAddState.Data> {
                it.copy(selectedColor = event.color)
            }
        }
    }
}