package com.harper.core.ui

import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harper.core.ext.orElse
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

abstract class ComponentViewModel<State, Event>(initialState: State) : ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext = viewModelScope.coroutineContext + CoroutineExceptionHandler { _, throwable ->
        message(Message.Snackbar(throwable.localizedMessage.orElse("Undefined error")))
        Timber.e(throwable)
    }
    val state: StateFlow<State>
        get() = mutableState.asStateFlow()
    private val mutableState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val messageState: StateFlow<Message?>
        get() = messageMutableState.asStateFlow()
    private val messageMutableState: MutableStateFlow<Message?> = MutableStateFlow(null)
    private var isComposed = false

    abstract fun onEvent(event: Event)

    fun onComposition() {
        if (!isComposed) {
            onFirstComposition()
        }
        isComposed = true
    }

    protected open fun onFirstComposition() {}

    protected fun update(mutation: (State) -> State) {
        check(Looper.getMainLooper().isCurrentThread) { "Unsafe change of state is forbidden! Use main thread" }
        mutableState.update(mutation)
    }

    protected fun message(message: Message) {
        messageMutableState.tryEmit(message)
    }

    protected suspend fun <T> withLoading(onLoading: (Boolean) -> Unit, block: suspend () -> T): T = coroutineScope {
        onLoading.invoke(true)
        coroutineContext[Job]
            ?.invokeOnCompletion {
                onLoading.invoke(false)
            }
        block.invoke()
    }
}
