package com.harper.capital.database

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.concurrent.Callable

class DatabaseTx(private val database: Database) {

    suspend fun <T> runSuspended(transaction: suspend () -> T): T = coroutineScope {
        withContext((Dispatchers.IO)) {
            database.runInTransaction(Callable { runBlocking { transaction.invoke() } })
        }
    }
}
