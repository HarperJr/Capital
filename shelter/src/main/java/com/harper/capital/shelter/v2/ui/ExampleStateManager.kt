package com.harper.capital.shelter.v2.ui

import com.harper.capital.shelter.v2.StateManager
import com.harper.capital.shelter.v2.ui.model.ExampleState

internal class ExampleStateManager : StateManager<ExampleState>() {
    override var state: ExampleState = ExampleState(dataList = emptyList())

    fun update(dataList: List<Any>): ExampleState {
        state = state.copy(dataList = dataList)
        return state
    }
}
