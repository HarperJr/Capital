package com.harper.core.ext

import kotlinx.coroutines.*

fun <T> CoroutineScope.lazyAsync(dispatcher: CoroutineDispatcher = Dispatchers.Unconfined, call: suspend () -> T): Deferred<T> =
    async(dispatcher, start = CoroutineStart.LAZY) { call.invoke() }
