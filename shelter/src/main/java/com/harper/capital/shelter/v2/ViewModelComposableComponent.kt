package com.harper.capital.shelter.v2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.java.KoinJavaComponent.getKoin
import org.koin.mp.KoinPlatformTools

internal abstract class ViewModelComposableComponent<S, E, RE, SM : StateManager<S>>(private val screen: Enum<*>) : ViewModel(),
    ComposableComponent<S, E> {
    override val coroutineContext = viewModelScope.coroutineContext + CoroutineExceptionHandler { _, throwable -> postError(throwable) }
    override val scope: Scope by lazy { getKoin().getOrCreateScope(KoinPlatformTools.generateId(), named(screen)) }
    protected val stateManager: SM by lazy { initStateManger() }

    private val _routeEvents: MutableSharedFlow<RE> =
        MutableSharedFlow(replay = 1, extraBufferCapacity = 0, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val routeEvents: Flow<RE> get() = _routeEvents
    private val _state: MutableStateFlow<S> = MutableStateFlow(stateManager.state)
    override val state: StateFlow<S> get() = _state
    private val _errorState: MutableStateFlow<Throwable?> = MutableStateFlow(null)
    override val errorState: StateFlow<Throwable?> get() = _errorState
    private val _loadingState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val loadingState: StateFlow<Boolean> get() = _loadingState

    override fun postEvent(event: E) = onEvent(event)

    override fun postError(error: Throwable) {
        _errorState.tryEmit(error)
    }

    override fun postLoading(isLoading: Boolean) {
        _loadingState.tryEmit(isLoading)
    }

    abstract fun initStateManger(): SM

    abstract fun onEvent(event: E)

    protected fun postRouteEvent(event: RE) = _routeEvents.tryEmit(event)

    protected fun postUiState(state: S) = _state.tryEmit(state)
}

internal abstract class SubComposableComponent<S, E, SM : StateManager<S>>(parent: ComposableComponent<*, *>) :
    ComposableComponent<S, E> {
    override val coroutineContext = parent.coroutineContext + CoroutineExceptionHandler { _, throwable -> postError(throwable) }
    override val scope: Scope = parent.scope
    protected val stateManager: SM by lazy { initStateManger() }

    private val _state: MutableStateFlow<S> = MutableStateFlow(stateManager.state)
    override val state: StateFlow<S> get() = _state
    private val _errorState: MutableStateFlow<Throwable?> = MutableStateFlow(null)
    override val errorState: StateFlow<Throwable?> get() = _errorState
    private val _loadingState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val loadingState: StateFlow<Boolean> get() = _loadingState

    override fun postEvent(event: E) = onEvent(event)

    override fun postError(error: Throwable) {
        _errorState.tryEmit(error)
    }

    override fun postLoading(isLoading: Boolean) {
        _loadingState.tryEmit(isLoading)
    }

    abstract fun initStateManger(): SM

    abstract fun onEvent(event: E)
}
