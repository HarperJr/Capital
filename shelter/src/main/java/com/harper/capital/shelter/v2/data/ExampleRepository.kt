package com.harper.capital.shelter.v2.data

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

internal class ExampleRepository(private val dao: ExampleDao) {

    fun getDataList(): Flow<List<Any>> = dao.getDataList()

    suspend fun fetchDataList(): List<Any> = coroutineScope {
        delay(4000L)
        listOf(Any())
    }
}
