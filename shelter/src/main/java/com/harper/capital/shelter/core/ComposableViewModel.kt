package com.harper.capital.shelter.core

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class ComposableViewModel<State, Event>(initialState: State) : ViewModel() {
    val state: StateFlow<State>
        get() = mutableState
    private val mutableState: MutableStateFlow<State> = MutableStateFlow(initialState)
    private var isStarted = false

    abstract fun onEvent(event: Event)

    open fun onFirstCompose() {}

    protected fun mutateState(mutation: (State) -> State) {
        mutableState.value = mutation(mutableState.value)
    }
}
