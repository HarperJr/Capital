package com.harper.capital.shelter.v2.ui

import com.harper.capital.shelter.v2.StateManager
import com.harper.capital.shelter.v2.ui.model.SubExampleState

internal class SubExampleStateManager : StateManager<SubExampleState>() {
    override var state: SubExampleState = SubExampleState(emptyList())
}
