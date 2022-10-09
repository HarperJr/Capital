package com.harper.capital.shelter.v2

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.scope.Scope
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

internal interface ComposableComponent<State, Event> : CoroutineScope {
    val scope: Scope
    val state: StateFlow<State>
    val errorState: StateFlow<Throwable?>
    val loadingState: StateFlow<Boolean>

    fun postEvent(event: Event)

    fun postError(error: Throwable)

    fun postLoading(isLoading: Boolean)

    suspend fun withLoading(block: suspend () -> Unit) {
        postLoading(true)
        block.invoke()
        postLoading(false)
    }

    fun launchWithError(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        onError: (Throwable) -> Unit,
        block: suspend CoroutineScope.() -> Unit
    ): Job = launch(context, start) {
        try {
            block.invoke(this)
        } catch (ex: Exception) {
            postLoading(false)
            if (ex is CancellationException) {
                throw ex
            } else {
                onError.invoke(ex)
            }
        }
    }
}
