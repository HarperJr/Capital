package com.harper.capital.shelter.core

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class ComposableViewModel<State, Event>(defaultState: State) : ViewModel() {
    val state: StateFlow<State>
        get() = mutableState
    private val mutableState: MutableStateFlow<State> = MutableStateFlow(defaultState)
    private var isStarted = false

    abstract fun onEvent(event: Event)

    fun start() {
        if (!isStarted) {
            isStarted = true
            onFirstStart()
        }
    }

    open fun onFirstStart() {}

    protected fun mutateState(mutation: (State) -> State) {
        mutableState.value = mutation(mutableState.value)
    }
}
