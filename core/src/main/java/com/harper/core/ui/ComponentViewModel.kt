package com.harper.core.ui

import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class ComponentViewModel<S : Any>(defaultState: S) : ViewModel() {
    val state: StateFlow<S>
        get() = _state
    private val _state: MutableStateFlow<S> = MutableStateFlow(defaultState)
    private var isStarted: Boolean = false

    fun start() {
        if (!isStarted) {
            onFirstStart()
        }
        isStarted = true
    }

    protected open fun onFirstStart() {}

    protected fun mutateState(mutation: (S) -> S) {
        check(Looper.getMainLooper().isCurrentThread) { "Unsafe change of state is forbidden! Use main thread" }
        _state.value = mutation.invoke(_state.value)
    }

    protected fun mutateReducedState(mutation: StateMutation<S>.() -> Unit) {
        check(Looper.getMainLooper().isCurrentThread) { "Unsafe change of state is forbidden! Use main thread" }
        val stateMutation = StateMutation(_state)
            .also { mutation.invoke(it) }
        stateMutation.newState?.let { _state.value = it }
    }

    protected fun launch(
        context: CoroutineContext = EmptyCoroutineContext,
        closure: suspend CoroutineScope.() -> Unit
    ) =
        viewModelScope.launch(context) { closure.invoke(this) }

    class StateMutation<S : Any>(private val stateFlow: StateFlow<S>) {
        val state: S
            get() = stateFlow.value
        var newState: S? = null

        inline fun <reified FS : S> forState(mutation: (FS) -> FS) {
            val currentState = state
            if (currentState is FS) {
                newState = mutation.invoke(currentState)
            }
        }
    }
}