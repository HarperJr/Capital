package com.harper.capital.database

import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

interface DatabaseTx {

    suspend fun <T> runSuspended(context: CoroutineContext = EmptyCoroutineContext, transaction: suspend () -> T): T
}
