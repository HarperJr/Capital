package com.harper.core.ui

import android.os.Looper
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class ComponentViewModel<S : Any>(private val defaultState: S) : ViewModel() {
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

    protected fun mutateState(mutation: S.() -> S) {
        check(Looper.getMainLooper().isCurrentThread) { "Unsafe change of state is forbidden! Use main thread" }
        _state.value = mutation.invoke(_state.value)
    }

    protected fun mutateReducedState(mutation: StateMutation<S>.() -> Unit) {
        check(Looper.getMainLooper().isCurrentThread) { "Unsafe change of state is forbidden! Use main thread" }
        val stateMutation = StateMutation<S>()
            .also { mutation.invoke(it) }
        stateMutation.newState?.let { _state.value = it }
    }

    inner class StateMutation<S : Any> {
        var newState: S? = null

        inline fun <reified FS : S> forState(mutation: S.() -> FS) {
            val state = state.value
            if (state is FS) {
                newState = mutation.invoke(state)
            }
        }
    }
}