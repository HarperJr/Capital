package com.harper.core.ui

import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class ComponentViewModelV1<State, Event>(defaultState: State) : ViewModel() {
    val state: StateFlow<State>
        get() = mutableState
    private val mutableState: MutableStateFlow<State> = MutableStateFlow(defaultState)
    private var isStarted = false

    abstract fun onEvent(event: Event)

    fun start() {
        if (!isStarted) {
            onFirstStart()
        }
        isStarted = true
    }

    open fun onFirstStart() {}

    protected fun update(mutation: (State) -> State) {
        check(Looper.getMainLooper().isCurrentThread) { "Unsafe change of state is forbidden! Use main thread" }
        mutableState.update(mutation)
    }

    protected fun launch(
        context: CoroutineContext = EmptyCoroutineContext,
        closure: suspend CoroutineScope.() -> Unit
    ) =
        viewModelScope.launch(context = context) { closure.invoke(this) }
}
