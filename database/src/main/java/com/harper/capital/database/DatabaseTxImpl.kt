package com.harper.capital.database

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.concurrent.Callable
import kotlin.coroutines.CoroutineContext

internal class DatabaseTxImpl(private val database: Database) : DatabaseTx {

    override suspend fun <T> runSuspended(context: CoroutineContext, transaction: suspend () -> T): T =
        withContext(context) {
            database.runInTransaction(Callable { runBlocking { transaction.invoke() } })
        }
}
